package com.eylulcan.moviefragment.ui.discover

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.model.ResultMovie
import com.google.firebase.auth.FirebaseAuth
import com.eylulcan.moviefragment.databinding.FragmentDiscoverBinding

class DiscoverFragment : Fragment(), MovieListener, Toolbar.OnMenuItemClickListener {

    private lateinit var fragmentBinding: FragmentDiscoverBinding
    private val discoverViewModel: DiscoverViewModel by viewModels()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var lastLoadedPage: Int = 1
    private var nowPlayingResultList: ArrayList<ResultMovie> = arrayListOf()
    private lateinit var nowPlayingAdapter: DiscoverAdapter
    private lateinit var topRatedAdapter: DiscoverAdapter
    private lateinit var mostPopularAdapter: DiscoverAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        val view = inflater.inflate(R.layout.fragment_discover, container, false)
        fragmentBinding = FragmentDiscoverBinding.bind(view)
        setToolbarMenu()
        fragmentBinding.discoverThirdRecyclerView.layoutManager = GridLayoutManager(context, 3)
        fragmentBinding.discoverTopRatedRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        fragmentBinding.discoverPopularRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeViewModel()
        discoverViewModel.getPopularMovieList()
        discoverViewModel.getTopRatedMovieList()
        discoverViewModel.getNowPlayingMovieList()
        postponeEnterTransition()
        fragmentBinding.discoverPopularRecyclerView.doOnPreDraw {
            startPostponedEnterTransition()
        }
        fragmentBinding.discoverTopRatedRecyclerView.doOnPreDraw {
            startPostponedEnterTransition()
        }
        fragmentBinding.discoverThirdRecyclerView.doOnPreDraw {
            startPostponedEnterTransition()
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
                nowPlayingResultList.addAll(movie.results ?: arrayListOf())
                nowPlayingAdapter.movieResultList = nowPlayingResultList
                nowPlayingAdapter.notifyDataSetChanged()
            }
        })
    }
    private fun setupUI(){
        topRatedAdapter = DiscoverAdapter(this, getString(R.string.top_rated))
        fragmentBinding.discoverTopRatedRecyclerView.adapter = topRatedAdapter
        nowPlayingAdapter = DiscoverAdapter(this, getString(R.string.now_playing))
        fragmentBinding.discoverThirdRecyclerView.adapter = nowPlayingAdapter
        mostPopularAdapter = DiscoverAdapter(this, getString(R.string.most_popular))
        fragmentBinding.discoverPopularRecyclerView.adapter = mostPopularAdapter

        fragmentBinding.discoverThirdRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    lastLoadedPage += 1
                    discoverViewModel.getNowPlayingMovieList(pageNo = lastLoadedPage)
                }
            }
        })
    }

    override fun onMovieClicked(resultMovie: ResultMovie, image: ImageView) {
        val movieDataBundle = bundleOf((getString(R.string.movieId)) to resultMovie.id)
        val extras = FragmentNavigatorExtras(image to getString(R.string.list_to_detail_transition))
        image.transitionName = image.id.toString()
        this.parentFragment?.parentFragment?.findNavController()?.navigate(
            R.id.action_dashboardFragment_to_movieDetailFragment,
            movieDataBundle, null, extras
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
            this.parentFragment?.parentFragment?.findNavController()?.navigate(R.id.action_dashboardFragment_to_searchFragment)
        }
        return false
    }

    private fun setToolbarMenu() {
        val toolbar = fragmentBinding.toolbar
        toolbar.inflateMenu(R.menu.menu)
        toolbar.setOnMenuItemClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        lastLoadedPage = 1
        val size = nowPlayingResultList.size
        nowPlayingResultList.clear()
        nowPlayingAdapter.notifyItemRangeRemoved(0, size)
    }

}