package com.eylulcan.moviefragment.ui.genres

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentGenresBinding

class GenresFragment : Fragment(), GenreListener {

    private val genreViewModel: GenresViewModel by viewModels()
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
                genreListAdapter = GenresAdapter(genreList, this)
                binding.genresFragmentRecyclerView.adapter = genreListAdapter
            }
        })
    }

    override fun onGenreClicked(id: Int) {
        val genreIdBundle = bundleOf("genreId" to id)
        this.parentFragment?.parentFragment?.findNavController()?.navigate(
            R.id.action_dashboardFragment_to_genreMovieListFragment,
            genreIdBundle,
            null,
            null
        )
    }

}