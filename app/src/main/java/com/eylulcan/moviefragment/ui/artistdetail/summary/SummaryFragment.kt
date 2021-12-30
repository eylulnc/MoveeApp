package com.eylulcan.moviefragment.ui.artistdetail.summary

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentSummaryBinding
import com.eylulcan.moviefragment.ui.artistdetail.ArtistDetailViewModel
import com.eylulcan.moviefragment.util.Utils

class SummaryFragment : Fragment() {

    private val artistDetailViewModel: ArtistDetailViewModel by activityViewModels()
    private lateinit var binding: FragmentSummaryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_summary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSummaryBinding.bind(view)
        if(Utils.isTablet(requireContext())){
            binding.summaryTextView.textSize = 18F
        }
        observeViewModel()
    }

    private fun observeViewModel(){
        artistDetailViewModel.artistDetail.observe(viewLifecycleOwner, { detail ->
            if (detail.biography.isNullOrEmpty()) {
                binding.summaryTextView.gravity = Gravity.CENTER
                binding.summaryTextView.text = getString(R.string.noInfo)
            } else {
                binding.summaryTextView.gravity = Gravity.NO_GRAVITY
                binding.summaryTextView.text = detail.biography
            }
        })
    }
}