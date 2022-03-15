package com.eylulcan.moveetime.ui.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.eylulcan.moveetime.R
import com.eylulcan.moveetime.databinding.FragmentSliderBinding
import com.eylulcan.moveetime.domain.util.Utils
import com.eylulcan.moveetime.ui.ItemListener

private const val IMAGE_MULTIPLIER = 1.5

class SliderFragment(private val movieInfo: Pair<Int, String>, private val glide: RequestManager) :
    Fragment(),
    ItemListener {

    private lateinit var binding: FragmentSliderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_slider, container, false)
        binding = FragmentSliderBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        glide.load(setImageUrl(movieInfo.second)).into(binding.sliderImageView)
        binding.sliderLayout.setOnClickListener {
            onItemClicked(movieInfo.first)
        }

    }

    private fun setupUI() {
        if (Utils.isTablet(requireContext())) {
            val sliderHeight = binding.sliderImageView.layoutParams.height
            binding.sliderImageView.layoutParams.height = sliderHeight.times(IMAGE_MULTIPLIER).toInt()
        }
    }

    override fun onItemClicked(id: Int) {
        val movieDataBundle = bundleOf((getString(R.string.movieId)) to id)
        findNavController().navigate(
            R.id.action_dashboardFragment_to_movieDetailFragment,
            movieDataBundle, null, null
        )
    }

    fun setImageUrl(poster_path: String?): String =
        Utils.BASE_IMAGE_URL_ORIGINAL.plus(poster_path)

}