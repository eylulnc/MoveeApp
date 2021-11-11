package com.eylulcan.moviefragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eylulcan.moviefragment.databinding.FragmentMovieDetailBinding

class MovieDetailFragment : Fragment() {

    private lateinit var fragmentBinding: FragmentMovieDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentBinding = FragmentMovieDetailBinding.bind(view)
        val param = arguments?.get(getString(R.string.movie)) as Movie
        fragmentBinding.movieImage.setImageResource(param.image)
        fragmentBinding.movieName.text = param.name
        fragmentBinding.movieInfo.text = param.info
    }
}