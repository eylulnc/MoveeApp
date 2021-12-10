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
import androidx.recyclerview.widget.LinearLayoutManager
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentDiscoverBinding
import com.eylulcan.moviefragment.model.ResultMovie
import com.google.firebase.auth.FirebaseAuth

class DiscoverFragment : Fragment(), MovieListener, Toolbar.OnMenuItemClickListener  {

    private lateinit var fragmentBinding: FragmentDiscoverBinding
    private val discoverViewModel: DiscoverViewModel by viewModels()
    private lateinit var discoverListRecyclerAdapter: DiscoverAdapter
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        val view =  inflater.inflate(R.layout.fragment_discover, container, false)
        fragmentBinding = FragmentDiscoverBinding.bind(view)
        setToolbarMenu()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentBinding.discoverPopularRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        fragmentBinding.discoverTopRatedRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        observeViewModel()
        discoverViewModel.getPopularMovieList()
        discoverViewModel.getTopRatedMovieList()
        postponeEnterTransition()
        fragmentBinding.discoverPopularRecyclerView.doOnPreDraw {
            startPostponedEnterTransition()
        }
        fragmentBinding.discoverTopRatedRecyclerView.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    override fun onMovieClicked(resultMovie: ResultMovie, image: ImageView) {
        val movieDataBundle = bundleOf((getString(R.string.movieId)) to resultMovie.id)
        val extras = FragmentNavigatorExtras(image to getString(R.string.list_to_detail_transition))
        image.transitionName = image.id.toString()
        //movieAdapter.updateMovieList(movieList)
        this.parentFragment?.parentFragment?.findNavController()?.navigate(
            R.id.action_dashboardFragment_to_movieDetailFragment,
            movieDataBundle, null, extras
        )
    }

    private fun observeViewModel() {
        discoverViewModel.popularMovies.observe(viewLifecycleOwner, { movieData ->
            movieData?.let { movie ->
                discoverListRecyclerAdapter = DiscoverAdapter(movie, this)
                fragmentBinding.discoverPopularRecyclerView.adapter = discoverListRecyclerAdapter
            }
        })
        discoverViewModel.topRatedMovies.observe(viewLifecycleOwner, { movieData ->
            movieData?.let { movie ->
                discoverListRecyclerAdapter = DiscoverAdapter(movie, this)
                fragmentBinding.discoverTopRatedRecyclerView.adapter = discoverListRecyclerAdapter
            }
        })
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
}