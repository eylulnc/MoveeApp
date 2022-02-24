package com.eylulcan.moviefragment.ui.moviedetail.cast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentCastBinding
import com.eylulcan.moviefragment.domain.util.Utils
import com.eylulcan.moviefragment.ui.ItemListener
import com.eylulcan.moviefragment.ui.moviedetail.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val SPAN_COUNT_TABLET = 2

@AndroidEntryPoint
class CastFragment @Inject constructor() : Fragment(), ItemListener {

    @Inject
    lateinit var castAdapter: CastAdapter
    private val movieDetailViewModel: DetailViewModel by activityViewModels()
    private var _binding: FragmentCastBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        if (Utils.isTablet(requireContext())) {
            binding.castRecyclerView.layoutManager = GridLayoutManager(requireContext().applicationContext, SPAN_COUNT_TABLET)
        } else {
            binding.castRecyclerView.layoutManager =
                LinearLayoutManager(requireContext().applicationContext, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun observeViewModel() {
        movieDetailViewModel.cast.observe(viewLifecycleOwner) { movieCredits ->
            movieCredits?.let { creditsEntity ->
                if (creditsEntity.cast.isEmpty()) {
                    binding.textView.isVisible = true
                    binding.castRecyclerView.isVisible = false
                } else {
                    binding.castRecyclerView.adapter = castAdapter
                    castAdapter.movieCredits = creditsEntity.cast
                    castAdapter.setOnItemClickListener {
                        onItemClicked(it)
                    }
                }
            }
        }
    }

    override fun onItemClicked(id: Int) {
        val artistIdBundle = bundleOf(getString(R.string.artistId) to id)
        this.parentFragment?.parentFragment?.findNavController()?.navigate(
            R.id.action_movieDetailFragment_to_artistDetailFragment, artistIdBundle, null, null
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.castRecyclerView.adapter = null
        _binding = null

    }

}