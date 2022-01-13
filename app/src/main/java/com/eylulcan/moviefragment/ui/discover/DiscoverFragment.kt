package com.eylulcan.moviefragment.ui.discover

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.eylulcan.moviefragment.ItemListener
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentDiscoverBinding
import com.eylulcan.moviefragment.model.ResultMovie
import com.eylulcan.moviefragment.util.Utils
import com.google.firebase.auth.FirebaseAuth
import me.samlss.broccoli.Broccoli

private const val GRID_COUNT = 3

class DiscoverFragment : Fragment(), ItemListener, Toolbar.OnMenuItemClickListener {

    private lateinit var fragmentBinding: FragmentDiscoverBinding
    private val discoverViewModel: DiscoverViewModel by activityViewModels()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var nowPlayingList: ArrayList<ResultMovie> = arrayListOf()
    private var topRatedList: ArrayList<ResultMovie> = arrayListOf()
    private var mostPopularList: ArrayList<ResultMovie> = arrayListOf()
    private lateinit var sharedPreferenceForSessionID: SharedPreferences
    private var sessionID: String? = null
    private val allListItems: ArrayList<ArrayList<ResultMovie>> = arrayListOf()
    private lateinit var recyclerViewAdapter: DiscoverParentAdapter
    private val placeholderNeeded = arrayListOf<View>()
    private var broccoli = Broccoli()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferenceForSessionID = requireContext().getSharedPreferences(
            getString(R.string.app_package_name), Context.MODE_PRIVATE
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_discover, container, false)
        fragmentBinding = FragmentDiscoverBinding.bind(view)
        setPlaceholders()
        setToolbarMenu()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeViewModel()
        allListItems.clear()
        discoverViewModel.getPopularMovieList()
        discoverViewModel.getUpcomingMovieList()
        sessionID = sharedPreferenceForSessionID.getString(getString(R.string.sessionId), null)
        if (sessionID == null) {
            discoverViewModel.getGuestSession()
        }
    }

    private fun observeViewModel() {
        discoverViewModel.popularMovies.observe(viewLifecycleOwner, { movieData ->
            movieData?.let { movie ->
                mostPopularList.clear()
                mostPopularList.addAll(movie.results as ArrayList<ResultMovie>)
                allListItems.add(mostPopularList)
                discoverViewModel.getTopRatedMovieList()
            }
        })
        discoverViewModel.topRatedMovies.observe(viewLifecycleOwner, { movieData ->
            movieData?.let { movie ->
                topRatedList.clear()
                topRatedList.addAll(movie.results as ArrayList<ResultMovie>)
                allListItems.add(topRatedList)
                discoverViewModel.getNowPlayingMovieList()
            }
        })
        discoverViewModel.nowPlaying.observe(viewLifecycleOwner, { movieData ->
            movieData?.let { movie ->
                val results = movie.results
                val restValue: Int = movie.results?.size?.mod(GRID_COUNT) ?: 0
                restValue.let {
                    nowPlayingList.clear()
                    nowPlayingList.addAll(results?.dropLast(it) as ArrayList<ResultMovie>)
                }
                allListItems.add(nowPlayingList)
                fragmentBinding.discoverMainRecyclerView.apply {
                    layoutManager = LinearLayoutManager(
                        this@DiscoverFragment.context,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    recyclerViewAdapter = DiscoverParentAdapter(this@DiscoverFragment)
                    adapter = recyclerViewAdapter
                    recyclerViewAdapter.movieResults = allListItems
                }
            }
        })
        discoverViewModel.sessionId.observe(viewLifecycleOwner, { session ->
            sharedPreferenceForSessionID.edit()
                .putString(getString(R.string.sessionId), session.sessionID).commit()
        })
        discoverViewModel.upcomingMovies.observe(viewLifecycleOwner, { movies ->
            removePlaceholders()
            val moviesInfo: ArrayList<Pair<Int, String>> =
                setMoviesInfo(movies.results as java.util.ArrayList<ResultMovie>)
            fragmentBinding.discoverSlider.adapter = SliderAdapter(this, moviesInfo)
            fragmentBinding.dotsIndicator.setViewPager2(fragmentBinding.discoverSlider)
            sliderScroll()
            removePlaceholders()
        })
    }

    private fun setupUI() {
        if (Utils.isTablet(requireContext())) {
            val height = fragmentBinding.discoverMainRecyclerView.layoutParams.height
            fragmentBinding.discoverMainRecyclerView.layoutParams.height =
                (height * 1.1).toInt()
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.logout) {
            auth.signOut()
            Toast.makeText(context, R.string.logged_out_movie_list, Toast.LENGTH_LONG).show()
            this.parentFragment?.parentFragment?.findNavController()?.navigate(
                R.id.action_dashboardFragment_to_loginFragment, null,
                NavOptions.Builder().setPopUpTo(R.id.dashboardFragment, true).build()
            )
            return true
        }
        return false
    }

    private fun setToolbarMenu() {
        val toolbar = fragmentBinding.toolbar
        toolbar.inflateMenu(R.menu.menu)
        toolbar.setOnMenuItemClickListener(this)
    }

    private fun setMoviesInfo(movieResults: ArrayList<ResultMovie>): ArrayList<Pair<Int, String>> {
        val pairList: ArrayList<Pair<Int, String>> = arrayListOf()
        for (i in 0..5) {
            val item = movieResults[i]
            item.let {
                pairList.add(Pair(item.id, item.backdropPath) as Pair<Int, String>)
            }
        }
        return pairList
    }

    private fun setPlaceholders() {
        placeholderNeeded.addAll(
            arrayListOf(
                fragmentBinding.sliderTemplate
            )
        )
        Utils.addPlaceholders(broccoli = broccoli, placeholderNeeded)
    }

    private fun removePlaceholders() {
        placeholderNeeded.forEach { view ->
            view.apply {
                broccoli.clearPlaceholder(this)
                this.isVisible = false
            }
        }
    }

    private fun sliderScroll() {
        val handler = Handler()
        val startPosition = 0

        val runnable = object : Runnable {
            override fun run() {
                val count = fragmentBinding.discoverSlider.adapter?.itemCount ?: 0
                if (fragmentBinding.discoverSlider.currentItem == count - 1 ) {
                    handler.postDelayed(this, 10000)
                    fragmentBinding.discoverSlider.setCurrentItem(startPosition, true)
                } else {
                    fragmentBinding.discoverSlider.setCurrentItem(fragmentBinding.discoverSlider.currentItem + 1, true)
                    handler.postDelayed(this, 10000)
                }
            }
        }

        handler.post(runnable)
    }

    override fun onItemClicked(id: Int) {
        val movieDataBundle = bundleOf((getString(R.string.movieId)) to id)
        this.parentFragment?.parentFragment?.findNavController()?.navigate(
            R.id.action_dashboardFragment_to_movieDetailFragment,
            movieDataBundle, null, null
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        discoverViewModel.setListsToDefault()
    }

}