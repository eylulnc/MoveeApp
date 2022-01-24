package com.eylulcan.moviefragment.ui.moviedetail.cast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.eylulcan.moviefragment.ItemListener
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentCastBinding
import com.eylulcan.moviefragment.ui.moviedetail.DetailViewModel
import com.eylulcan.moviefragment.util.Utils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val SPAN_COUNT_TABLET = 2

@AndroidEntryPoint
class CastFragment : Fragment(), ItemListener {

    private lateinit var binding: FragmentCastBinding
    @Inject
    lateinit var castAdapter: CastAdapter
    private val movieDetailViewModel: DetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_cast, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCastBinding.bind(view)
        setupUI()

        observeViewModel()

    }
    private fun setupUI(){
        if ( Utils.isTablet(requireContext()) ) {
            binding.castRecyclerView.layoutManager = GridLayoutManager(context, SPAN_COUNT_TABLET)
        } else {
            binding.castRecyclerView.layoutManager = LinearLayoutManager( requireContext(),LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun observeViewModel() {
        movieDetailViewModel.cast.observe(viewLifecycleOwner, { movieCredits ->
            binding.castRecyclerView.adapter = castAdapter
            castAdapter.movieCredits = movieCredits.cast ?: emptyList()
            castAdapter.setOnItemClickListener {
                onItemClicked(it)
            }
        })
    }

    override fun onItemClicked(id: Int) {
        val artistIdBundle = bundleOf(getString(R.string.artistId) to id)
        this.parentFragment?.parentFragment?.findNavController()?.navigate(
            R.id.action_movieDetailFragment_to_artistDetailFragment, artistIdBundle, null, null
        )
    }

}