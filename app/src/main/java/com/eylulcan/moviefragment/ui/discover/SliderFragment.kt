package com.eylulcan.moviefragment.ui.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.eylulcan.moviefragment.ItemListener
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentSliderBinding
import com.eylulcan.moviefragment.util.Utils

class SliderFragment(private val movieInfo: Pair<Int,String>): Fragment(), ItemListener {

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
        Glide.with(binding.root).load(setImageUrl(movieInfo.second)).into(binding.sliderImageView)
        binding.sliderLayout.setOnClickListener {
            onItemClicked(movieInfo.first)
        }

    }

    override fun onItemClicked(id: Int) {
        println("encenc")
        val movieDataBundle = bundleOf((getString(R.string.movieId)) to id)
        this.parentFragment?.parentFragment?.parentFragment?.findNavController()?.navigate(
            R.id.action_dashboardFragment_to_movieDetailFragment,
            movieDataBundle, null, null
        )
    }

    fun setImageUrl(poster_path: String?): String =
        Utils.BASE_IMAGE_URL_ORIGINAL.plus(poster_path)

}