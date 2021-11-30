package com.eylulcan.moviefragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.eylulcan.moviefragment.databinding.FragmentGenresBinding

class GenresFragment : Fragment() {

    private val genreViewModel: MovieListViewModel by viewModels()
    private lateinit var binding: FragmentGenresBinding
    private lateinit var genreListAdapter: GenresAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_genres, container, false)
        binding = FragmentGenresBinding.bind(view)
        binding.genresFragmentRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        genreViewModel.getGenreList()
    }

    private fun observeViewModel() {
        genreViewModel.genres.observe(viewLifecycleOwner, { genreData ->
            genreData?.let { genreList ->
                genreListAdapter = GenresAdapter(genreList)
                binding.genresFragmentRecyclerView.adapter = genreListAdapter
            }
        })
    }
}