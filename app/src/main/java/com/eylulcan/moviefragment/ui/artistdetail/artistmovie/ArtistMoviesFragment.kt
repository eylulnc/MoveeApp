package com.eylulcan.moviefragment.ui.artistdetail.artistmovie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentArtistMoviesBinding
import com.eylulcan.moviefragment.ui.artistdetail.ArtistDetailViewModel

class ArtistMoviesFragment : Fragment(), ArtistMovieClickListener {

    private lateinit var binding: FragmentArtistMoviesBinding
    private lateinit var artistMovieAdapter: ArtistMovieAdapter
    private val artistDetailViewModel: ArtistDetailViewModel by activityViewModels()

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
        observeViewModel()
    }

    private fun observeViewModel() {
        artistDetailViewModel.artistMovieCredits.observe(viewLifecycleOwner, { movieCredits ->
            artistMovieAdapter = ArtistMovieAdapter(movieCredits, artistMovieClickListener = this)
            binding.artistMovieRecyclerView.adapter = artistMovieAdapter
        })
    }

    override fun onMovieClick(movieID: Int) {
        val movieDataBundle = bundleOf((getString(R.string.movieId)) to movieID)
        findNavController().navigate(
            R.id.action_artistDetailFragment_to_movieDetailFragment,
            movieDataBundle, null, null
        )
    }
}