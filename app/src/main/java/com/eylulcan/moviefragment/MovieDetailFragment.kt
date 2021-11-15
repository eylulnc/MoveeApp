package com.eylulcan.moviefragment

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eylulcan.moviefragment.databinding.FragmentMovieDetailBinding

class MovieDetailFragment : Fragment() {
    private lateinit var fragmentBinding: FragmentMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val selectedMovieDataArgument = arguments?.get(getString(R.string.movie)) as Movie
        fragmentBinding = FragmentMovieDetailBinding.bind(view)
        fragmentBinding.movieImage.setImageResource(selectedMovieDataArgument.image)
        fragmentBinding.movieName.text = selectedMovieDataArgument.name
        fragmentBinding.movieInfo.text = selectedMovieDataArgument.info

    }
}