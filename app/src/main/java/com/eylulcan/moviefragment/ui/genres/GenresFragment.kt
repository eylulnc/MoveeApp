package com.eylulcan.moviefragment.ui.genres

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.eylulcan.moviefragment.ui.ItemListener
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentGenresBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GenresFragment : Fragment(), ItemListener {

    private val genreViewModel: GenresViewModel by viewModels()
    private var _binding: FragmentGenresBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var genreListAdapter: GenresAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGenresBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.genresFragmentRecyclerView.layoutManager =
            GridLayoutManager(requireContext().applicationContext, 2)
        observeViewModel()
        genreViewModel.getGenreList()
    }

    private fun observeViewModel() {
        genreViewModel.genres.observe(viewLifecycleOwner) { genreData ->
            genreData?.let { genreList ->
                val list = genreList.genres
                binding.genresFragmentRecyclerView.adapter = genreListAdapter
                genreListAdapter.setOnItemClickListener { id ->
                    onItemClicked(id)
                }
                genreListAdapter.genreList = list
            }
        }
    }

    override fun onItemClicked(id: Int) {
        val genreIdBundle = bundleOf(getString(R.string.genreId) to id)
        this.parentFragment?.parentFragment?.findNavController()?.navigate(
            R.id.action_dashboardFragment_to_genreMovieListFragment,
            genreIdBundle,
            null,
            null
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.genresFragmentRecyclerView.adapter = null
        _binding = null
    }
}