package com.eylulcan.moviefragment

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.eylulcan.moviefragment.databinding.FragmentMovieListBinding
import com.google.firebase.auth.FirebaseAuth

class MovieListFragment : Fragment(), MovieListener {

    private lateinit var fragmentBinding: FragmentMovieListBinding
    private val movieListViewModel: MovieListViewModel by viewModels()
    private lateinit var movieListRecyclerAdapter: MovieAdapter
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
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
         findNavController().navigate(R.id.action_movieListFragment_to_movieDetailFragment, movieDataBundle,null, extras)
     }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout) {
            auth.signOut()
            Toast.makeText(context, R.string.logged_out_movie_list, Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_movieListFragment_to_loginFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun observeViewModel() {
        movieListViewModel.popularMovies.observe(viewLifecycleOwner, { movieData ->
            movieData?.let { movie ->
                movieListRecyclerAdapter = MovieAdapter(movie,this)
                fragmentBinding.movieListPopularRecyclerView.adapter = movieListRecyclerAdapter
            }
        })
        movieListViewModel.topRatedMovies.observe(viewLifecycleOwner, { movieData ->
            movieData?.let { movie ->
                movieListRecyclerAdapter = MovieAdapter(movie,this)
                fragmentBinding.movieListTopRatedRecyclerView.adapter = movieListRecyclerAdapter
            }
        })
    }
}