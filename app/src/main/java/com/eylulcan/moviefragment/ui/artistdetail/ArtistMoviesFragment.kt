package com.eylulcan.moviefragment.ui.artistdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentArtistMoviesBinding

class ArtistMoviesFragment : Fragment() {

    private lateinit var binding: FragmentArtistMoviesBinding
    private lateinit var artistMovieAdapter: ArtistMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_artist_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArtistMoviesBinding.bind(view)
        binding.artistMovieRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        artistMovieAdapter = ArtistMovieAdapter()
        binding.artistMovieRecyclerView.adapter = artistMovieAdapter
    }

}