package com.eylulcan.moviefragment.ui.detail

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentMovieDetailBinding
import com.eylulcan.moviefragment.model.ResultMovie

class MovieDetailFragment : Fragment() {

    companion object {
        private const val BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w185"
    }

    private lateinit var fragmentBinding: FragmentMovieDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val selectedResultMovieDataArgument =
            arguments?.get(getString(R.string.movie)) as ResultMovie
        fragmentBinding = FragmentMovieDetailBinding.bind(view)
        Glide.with(this).load(setImageUrl(selectedResultMovieDataArgument.posterPath))
            .into(fragmentBinding.movieImage)
        fragmentBinding.movieName.text = selectedResultMovieDataArgument.title
        fragmentBinding.movieInfo.text = selectedResultMovieDataArgument.overview
    }

    private fun setImageUrl(poster_path: String?): String {
        return BASE_IMAGE_URL.plus(poster_path)
    }
}