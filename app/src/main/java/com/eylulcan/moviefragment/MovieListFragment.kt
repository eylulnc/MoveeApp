package com.eylulcan.moviefragment

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.core.view.doOnPreDraw
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.eylulcan.moviefragment.databinding.FragmentMovieListBinding

class MovieListFragment : Fragment(), MovieListener {

    private val movieList: ArrayList<Movie> = arrayListOf()
    private lateinit var fragmentBinding: FragmentMovieListBinding
    private lateinit var movieAdapter:MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentBinding = FragmentMovieListBinding.bind(view)
        fragmentBinding.movieListRecyclerView.layoutManager = GridLayoutManager(context, 2)
        movieListGenerator()
        movieAdapter = MovieAdapter(movieList, this)
        fragmentBinding.movieListRecyclerView.adapter = movieAdapter
        postponeEnterTransition()
        fragmentBinding.movieListRecyclerView.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    private fun movieListGenerator() {
        val avengers = Movie(
            getString(R.string.avengers),
            R.drawable.avengers,
            getString(R.string.avengers_info)
        )
        val interstellar = Movie(
            getString(R.string.interstellar),
            R.drawable.interstellar,
            getString(R.string.interstellar_info)
        )
        val harrypotter = Movie(
            getString(R.string.harry_potter),
            R.drawable.harrypotter,
            getString(R.string.harry_potter_info)
        )
        val titanic = Movie(
            getString(R.string.titanic),
            R.drawable.titanic,
            getString(R.string.titanic_info)
        )
        movieList.add(avengers)
        movieList.add(interstellar)
        movieList.add(harrypotter)
        movieList.add(titanic)
    }

    override fun onMovieClicked(position: Int, image: ImageView) {
        val movie: Movie = movieList[position]
        val movieDataBundle = bundleOf((getString(R.string.movie)) to movie)
        val extras = FragmentNavigatorExtras(image to getString(R.string.transaction_name))
        image.transitionName = movie.name
        movieAdapter.updateMovieList(movieList)
        findNavController().navigate(R.id.action_movieListFragment_to_movieDetailFragment, movieDataBundle,null, extras)
    }
}