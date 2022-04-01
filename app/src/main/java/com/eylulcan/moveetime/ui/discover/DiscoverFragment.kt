package com.eylulcan.moveetime.ui.discover

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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.eylulcan.moveetime.R
import com.eylulcan.moveetime.databinding.FragmentDiscoverBinding
import com.eylulcan.moveetime.domain.entity.ResultMovieEntity
import com.eylulcan.moveetime.domain.util.ResultData
import com.eylulcan.moveetime.domain.util.Utils
import com.eylulcan.moveetime.ui.ItemListener
import dagger.hilt.android.AndroidEntryPoint
import me.samlss.broccoli.Broccoli
import javax.inject.Inject

private const val GRID_COUNT = 3
private const val START_POSITION = 0
private const val NO_ITEM = 0
private const val IMAGE_MULTIPLIER = 1.5
private const val RECYCLER_MULTIPLIER = 1.5
private const val DELAY: Long = 10000

@AndroidEntryPoint
class DiscoverFragment @Inject constructor() : Fragment(), ItemListener,
    Toolbar.OnMenuItemClickListener {

    @Inject
    lateinit var recyclerViewAdapter: DiscoverParentAdapter

    @Inject
    lateinit var sliderAdapter: SliderAdapter
    private var _binding: FragmentDiscoverBinding? = null
    private val fragmentBinding get() = _binding!!
    private val discoverViewModel: DiscoverViewModel by viewModels()
    private var nowPlayingList: ArrayList<ResultMovieEntity> = arrayListOf()
    private var topRatedList: ArrayList<ResultMovieEntity> = arrayListOf()
    private var mostPopularList: ArrayList<ResultMovieEntity> = arrayListOf()
    private lateinit var sharedPreferenceForSessionID: SharedPreferences
    private var sessionID: String? = null
    private val allListItems: ArrayList<ArrayList<ResultMovieEntity>> = arrayListOf()
    private var placeholderNeeded = arrayListOf<View>()
    private var broccoli: Broccoli? = Broccoli()
    private var handler: Handler? = null
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferenceForSessionID = requireContext().getSharedPreferences(
            getString(R.string.app_package_name), Context.MODE_PRIVATE
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        return fragmentBinding.root
    }

    override fun onResume() {
        super.onResume()
        observeViewModel()
        handler= Handler()
        discoverViewModel.getPopularMovieList()
        discoverViewModel.getUpcomingMovieList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setPlaceholders()
        setToolbarMenu()
        setupUI()
        allListItems.clear()
        sessionID = sharedPreferenceForSessionID.getString(getString(R.string.sessionId), null)
        if (sessionID == null) {
            discoverViewModel.getGuestSession()
        }
    }

    private fun observeViewModel() {
        discoverViewModel.popularMovies.observe(viewLifecycleOwner) { movieData ->
            movieData?.let { movie ->
                mostPopularList.clear()
                mostPopularList.addAll(movie.results as ArrayList<ResultMovieEntity>)
                allListItems.add(mostPopularList)
                discoverViewModel.getTopRatedMovieList()
            }
        }
        discoverViewModel.topRatedMovies.observe(viewLifecycleOwner) { movieData ->
            movieData?.let { movie ->
                topRatedList.clear()
                topRatedList.addAll(movie.results as ArrayList<ResultMovieEntity>)
                allListItems.add(topRatedList)
                discoverViewModel.getNowPlayingMovieList()
            }
        }
        discoverViewModel.nowPlaying.observe(viewLifecycleOwner) { movieData ->
            movieData?.let { movie ->
                val results = movie.results
                val restValue: Int = results.size.mod(GRID_COUNT)
                restValue.let {
                    nowPlayingList.clear()
                    nowPlayingList.addAll(results.dropLast(it) as ArrayList<ResultMovieEntity>)
                }
                allListItems.add(nowPlayingList)
                fragmentBinding.discoverMainRecyclerView.apply {
                    layoutManager = LinearLayoutManager(
                        this@DiscoverFragment.context,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    adapter = recyclerViewAdapter
                    recyclerViewAdapter.setOnItemClickListener {
                        onItemClicked(it)
                    }
                    recyclerViewAdapter.movieResults = allListItems
                }
            }
        }
        discoverViewModel.sessionEntityId.observe(viewLifecycleOwner) { session ->
            sharedPreferenceForSessionID.edit()
                .putString(getString(R.string.sessionId), session.sessionID).commit()
        }
        discoverViewModel.upcomingMovies.observe(viewLifecycleOwner) { movies ->
            val moviesInfo: ArrayList<Pair<Int, String>> =
                setMoviesInfo(movies.results as java.util.ArrayList<ResultMovieEntity>)
            fragmentBinding.discoverSlider.adapter = sliderAdapter
            sliderAdapter.updateList(moviesInfo)
            fragmentBinding.discoverSlider.let { fragmentBinding.dotsIndicator.setViewPager2(it) }
            sliderScroll()
            removePlaceholders()
        }
        discoverViewModel.signOut.observe(this.requireActivity()) {
            when (it) {
                is ResultData.Success -> {}
                else -> {}
            }
        }
    }

    private fun setupUI() {
        if (Utils.isTablet(requireContext())) {
            val recyclerViewHeight = fragmentBinding.discoverMainRecyclerView.layoutParams.height
            fragmentBinding.discoverMainRecyclerView.layoutParams.height =
                recyclerViewHeight.times(RECYCLER_MULTIPLIER).toInt()
            val sliderHeight = fragmentBinding.discoverSlider.layoutParams.height
            fragmentBinding.discoverSlider.layoutParams.height =
                sliderHeight.times(IMAGE_MULTIPLIER).toInt()
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.logout) {
            discoverViewModel.signOut()
            Toast.makeText(context, R.string.logged_out_movie_list, Toast.LENGTH_LONG).show()
            this.parentFragment?.parentFragment?.findNavController()?.navigate(
                R.id.action_dashboardFragment_to_loginFragment, null,
                NavOptions.Builder().setPopUpTo(R.id.dashboardFragment, true).build()
            )
            return true
        } else if (item?.itemId == R.id.visitedMovies) {
            this.parentFragment?.parentFragment?.findNavController()?.navigate(
                R.id.action_dashboardFragment_to_lastVisitedFragment
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

    private fun setMoviesInfo(movieResultEntities: ArrayList<ResultMovieEntity>): ArrayList<Pair<Int, String>> {
        val pairList: ArrayList<Pair<Int, String>> = arrayListOf()
        for (i in 0..5) {
            val item = movieResultEntities[i]
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
        broccoli?.let { Utils.addPlaceholders(broccoli = it, placeholderNeeded) }
    }

    private fun removePlaceholders() {
        placeholderNeeded.forEach { view ->
            view.apply {
                broccoli?.clearPlaceholder(this)
                this.visibility = View.INVISIBLE
            }
        }
    }

    private fun sliderScroll() {
        runnable = object : Runnable {
            override fun run() {
                val count = fragmentBinding.discoverSlider.adapter?.itemCount ?: NO_ITEM
                if (fragmentBinding.discoverSlider.currentItem == count.dec()) {
                    handler?.postDelayed(this, DELAY)
                    fragmentBinding.discoverSlider.setCurrentItem(START_POSITION, true)
                } else {
                    handler?.postDelayed(this, DELAY)
                    fragmentBinding.discoverSlider.setCurrentItem(
                        fragmentBinding.discoverSlider.currentItem.inc(),
                        true
                    )
                }
            }
        }
        handler?.post(runnable)
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
        handler?.removeCallbacks(runnable)
        fragmentBinding.discoverSlider.adapter = null
        fragmentBinding.discoverMainRecyclerView.adapter = null
        placeholderNeeded = arrayListOf()
        broccoli = null
        _binding = null
        handler = null
    }

    override fun onStop() {
        super.onStop()
        handler?.removeCallbacks(runnable)
        discoverViewModel.popularMovies.removeObservers(this)
        discoverViewModel.topRatedMovies.removeObservers(this)
        discoverViewModel.nowPlaying.removeObservers(this)
        discoverViewModel.upcomingMovies.removeObservers(this)
        discoverViewModel.setListsToDefault()
        fragmentBinding.discoverMainRecyclerView.adapter = null
        fragmentBinding.discoverSlider.adapter = null
        handler = null

    }
}