package com.eylulcan.moviefragment.ui.genres.movielist

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
import com.eylulcan.moviefragment.databinding.FragmentMoreBinding
import com.eylulcan.moviefragment.ui.genres.GenresViewModel
import com.eylulcan.moviefragment.ui.moviedetail.MovieDetailListener

class GenreMovieListFragment : Fragment(), MovieDetailListener {

    private lateinit var binding: FragmentMoreBinding
    private lateinit var genreMovieListAdapter: GenreMovieListAdapter
    private val genresViewModel: GenresViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_more, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMoreBinding.bind(view)
        val selectedGenreId = arguments?.get("genreId") as Int
        binding.moreRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        genresViewModel.getMovieListByGenre(selectedGenreId)
        observeViewModel()
    }

    private fun observeViewModel() {
        genresViewModel.movies.observe(viewLifecycleOwner, { movie ->
            genreMovieListAdapter = GenreMovieListAdapter(movie, this)
            binding.moreRecyclerView.adapter = genreMovieListAdapter
        })
    }

    override fun onMovieClicked(id: Int) {
        val movieIdBundle = bundleOf(getString(R.string.movieId) to id)
        findNavController().navigate(
            R.id.action_genreMovieListFragment_to_movieDetailFragment,
            movieIdBundle,
            null,
            null
        )
    }

}