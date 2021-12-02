package com.eylulcan.moviefragment.ui.movielist

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.eylulcan.moviefragment.MainActivity
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentMovieListBinding
import com.eylulcan.moviefragment.model.ResultMovie

class MovieListFragment : Fragment(), MovieListener {

    private lateinit var fragmentBinding: FragmentMovieListBinding
    private val movieListViewModel: MovieListViewModel by viewModels()
    private lateinit var movieListRecyclerAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        updateToolbar()
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentBinding = FragmentMovieListBinding.bind(view)
        fragmentBinding.movieListPopularRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        fragmentBinding.movieListTopRatedRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        observeViewModel()
        movieListViewModel.getPopularMovieList()
        movieListViewModel.getTopRatedMovieList()
        postponeEnterTransition()
        fragmentBinding.movieListPopularRecyclerView.doOnPreDraw {
            startPostponedEnterTransition()
        }
        fragmentBinding.movieListTopRatedRecyclerView.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    override fun onMovieClicked(resultMovie: ResultMovie, image: ImageView) {
        val movieDataBundle = bundleOf((getString(R.string.movie)) to resultMovie)
        val extras = FragmentNavigatorExtras(image to getString(R.string.list_to_detail_transition))
        image.transitionName = image.id.toString()
        //movieAdapter.updateMovieList(movieList)
        this.parentFragment?.parentFragment?.findNavController()?.navigate(
            R.id.action_dashboardFragment_to_movieDetailFragment,
            movieDataBundle, null, extras
        )
    }

    private fun observeViewModel() {
        movieListViewModel.popularMovies.observe(viewLifecycleOwner, { movieData ->
            movieData?.let { movie ->
                movieListRecyclerAdapter = MovieAdapter(movie, this)
                fragmentBinding.movieListPopularRecyclerView.adapter = movieListRecyclerAdapter
            }
        })
        movieListViewModel.topRatedMovies.observe(viewLifecycleOwner, { movieData ->
            movieData?.let { movie ->
                movieListRecyclerAdapter = MovieAdapter(movie, this)
                fragmentBinding.movieListTopRatedRecyclerView.adapter = movieListRecyclerAdapter
            }
        })
    }

    private fun updateToolbar(){
        val activity = activity as MainActivity
        activity.supportActionBar?.title = getString(R.string.discover)
    }
}