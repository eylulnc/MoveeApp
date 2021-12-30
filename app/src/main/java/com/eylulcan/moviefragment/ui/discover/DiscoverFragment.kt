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
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentDiscoverBinding
import com.eylulcan.moviefragment.model.ResultMovie
import com.google.firebase.auth.FirebaseAuth

class DiscoverFragment : Fragment(), MovieListener, Toolbar.OnMenuItemClickListener {

    private lateinit var fragmentBinding: FragmentDiscoverBinding
    private val discoverViewModel: DiscoverViewModel by viewModels()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var lastLoadedPageItems: List<ResultMovie>? = emptyList()
    private var nowPlayingResultList: ArrayList<ResultMovie> = arrayListOf()
    private lateinit var nowPlayingAdapter: DiscoverAdapter
    private lateinit var topRatedAdapter: DiscoverAdapter
    private lateinit var mostPopularAdapter: DiscoverAdapter
    private var enableToRequest: Boolean = false
    private lateinit var sharedPreferenceForSessionID: SharedPreferences
    private var sessionID: String? = null

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
        discoverViewModel.getTopRatedMovieList()
        discoverViewModel.getNowPlayingMovieList()
        discoverViewModel.lastLoadedPage++
        sessionID = sharedPreferenceForSessionID.getString(getString(R.string.sessionId), null)
        if (sessionID == null) {
            discoverViewModel.getGuestSession()
        }
    }

    private fun observeViewModel() {
        discoverViewModel.popularMovies.observe(viewLifecycleOwner, { movieData ->
            movieData?.let { movie ->
                mostPopularAdapter.movieResultList = movie.results
                mostPopularAdapter.notifyDataSetChanged()
            }
        })
        discoverViewModel.topRatedMovies.observe(viewLifecycleOwner, { movieData ->
            movieData?.let { movie ->
                topRatedAdapter.movieResultList = movie.results
                topRatedAdapter.notifyDataSetChanged()
            }
        })
        discoverViewModel.nowPlaying.observe(viewLifecycleOwner, { movieData ->
            movieData?.let { movie ->
                lastLoadedPageItems =
                    if (movie.results?.let { lastLoadedPageItems?.containsAll(it) } == true) {
                        emptyList()
                    } else {
                        movie.results
                    }
                nowPlayingResultList.addAll(lastLoadedPageItems ?: arrayListOf())
                nowPlayingAdapter.movieResultList = nowPlayingResultList
                enableToRequest = true
                nowPlayingAdapter.notifyDataSetChanged()
            }
        })
        discoverViewModel.sessionId.observe(viewLifecycleOwner, { session ->
            sharedPreferenceForSessionID.edit().putString(getString(R.string.sessionId), session.sessionID).commit()
        })

    }

    private fun setupUI() {
        fragmentBinding.discoverNowPlayingRecyclerView.layoutManager = GridLayoutManager(context, 3)
        fragmentBinding.discoverTopRatedRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        fragmentBinding.discoverPopularRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        topRatedAdapter = DiscoverAdapter(this, getString(R.string.top_rated))
        fragmentBinding.discoverTopRatedRecyclerView.adapter = topRatedAdapter
        nowPlayingAdapter = DiscoverAdapter(this, getString(R.string.now_playing))
        fragmentBinding.discoverNowPlayingRecyclerView.adapter = nowPlayingAdapter
        mostPopularAdapter = DiscoverAdapter(this, getString(R.string.most_popular))
        fragmentBinding.discoverPopularRecyclerView.adapter = mostPopularAdapter

        fragmentBinding.discoverNowPlayingRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager =
                    fragmentBinding.discoverNowPlayingRecyclerView.layoutManager as GridLayoutManager
                val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()
                if (lastVisiblePosition == nowPlayingResultList.size - 1 && enableToRequest) {
                    discoverViewModel.getNowPlayingMovieList()
                    discoverViewModel.lastLoadedPage++
                    enableToRequest = false
                }
            }
        })
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