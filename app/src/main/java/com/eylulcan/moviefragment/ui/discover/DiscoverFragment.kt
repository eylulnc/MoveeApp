package com.eylulcan.moviefragment.ui.discover

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentDiscoverBinding
import com.eylulcan.moviefragment.model.ResultMovie
import com.eylulcan.moviefragment.util.Utils
import com.google.firebase.auth.FirebaseAuth

private const val  GRID_COUNT = 3

class DiscoverFragment : Fragment(), MovieListener, Toolbar.OnMenuItemClickListener {

    private lateinit var fragmentBinding: FragmentDiscoverBinding
    private val discoverViewModel: DiscoverViewModel by activityViewModels()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var nowPlayingList: ArrayList<ResultMovie> = arrayListOf()
    private lateinit var sharedPreferenceForSessionID: SharedPreferences
    private var sessionID: String? = null
    private val allListItems: ArrayList<ArrayList<ResultMovie>> = arrayListOf()
    private lateinit var recyclerViewAdapter: FlexibleAdapter

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
        setToolbarMenu()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeViewModel()
        discoverViewModel.getPopularMovieList()
        discoverViewModel.lastLoadedPage++
        sessionID = sharedPreferenceForSessionID.getString(getString(R.string.sessionId), null)
        if (sessionID == null) {
            discoverViewModel.getGuestSession()
        }
    }

    private fun observeViewModel() {
        discoverViewModel.popularMovies.observe(viewLifecycleOwner, { movieData ->
            movieData?.let { movie ->
                allListItems.clear()
                allListItems.add(movie.results as java.util.ArrayList<ResultMovie>)
                discoverViewModel.getTopRatedMovieList()
            }
        })
        discoverViewModel.topRatedMovies.observe(viewLifecycleOwner, { movieData ->
            movieData?.let { movie ->
                allListItems.add(movie.results as java.util.ArrayList<ResultMovie>)
                discoverViewModel.getNowPlayingMovieList()
            }
        })
        discoverViewModel.nowPlaying.observe(viewLifecycleOwner, { movieData ->
            movieData?.let { movie ->
                val results = movie.results
                val restValue : Int = movie.results?.size?.mod(GRID_COUNT) ?: 0
                restValue.let {
                    nowPlayingList = results?.dropLast(it)  as ArrayList<ResultMovie>
                }
                allListItems.add(nowPlayingList)
                fragmentBinding.discoverMainRecyclerView.apply {
                    layoutManager = LinearLayoutManager(this@DiscoverFragment.context, LinearLayoutManager.VERTICAL, false )
                    recyclerViewAdapter = FlexibleAdapter( this@DiscoverFragment)
                    adapter = recyclerViewAdapter
                    recyclerViewAdapter.movieResults = allListItems
                    recyclerViewAdapter.notifyDataSetChanged()
                }
            }
        })
        discoverViewModel.sessionId.observe(viewLifecycleOwner, { session ->
            sharedPreferenceForSessionID.edit()
                .putString(getString(R.string.sessionId), session.sessionID).commit()
        })

    }

    private fun setupUI() {
        if (Utils.isTablet(requireContext())) {
            val height = fragmentBinding.discoverMainRecyclerView.layoutParams.height
            fragmentBinding.discoverMainRecyclerView.layoutParams.height =
                (height * 1.1).toInt()
        }
    }

    override fun onMovieClicked(resultMovie: ResultMovie) {
        val movieDataBundle = bundleOf((getString(R.string.movieId)) to resultMovie.id)
        this.parentFragment?.parentFragment?.findNavController()?.navigate(
            R.id.action_dashboardFragment_to_movieDetailFragment,
            movieDataBundle, null, null
        )
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
        } else if (item?.itemId == R.id.search_button) {
            this.parentFragment?.parentFragment?.findNavController()
                ?.navigate(R.id.action_dashboardFragment_to_searchFragment)
        }
        return false
    }

    private fun setToolbarMenu() {
        val toolbar = fragmentBinding.toolbar
        toolbar.inflateMenu(R.menu.menu)
        toolbar.setOnMenuItemClickListener(this)
    }

}