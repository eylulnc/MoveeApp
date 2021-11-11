package com.eylulcan.moviefragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.eylulcan.moviefragment.databinding.FragmentMovieListBinding

class MovieListFragment : Fragment(), MovieListener {

    private lateinit var movieList: ArrayList<Movie>
    private lateinit var fragmentBinding: FragmentMovieListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentBinding = FragmentMovieListBinding.bind(view)
        movieList = ArrayList()
        movieListGenerator()
        fragmentBinding.recyclerView.layoutManager = GridLayoutManager(context, 2)
        val movieAdapter = MovieAdapter(movieList, this)
        fragmentBinding.recyclerView.adapter = movieAdapter
    }

    private fun movieListGenerator() {
        println(getString(R.string.avengers))
        println(R.string.avengers.toString())
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

    override fun onMovieClicked(position: Int) {
        val movie: Movie = movieList[position]
        val param = bundleOf((getString(R.string.movie)) to movie)
        findNavController().navigate(R.id.action_movieListFragment_to_movieDetailFragment, param)
    }
}