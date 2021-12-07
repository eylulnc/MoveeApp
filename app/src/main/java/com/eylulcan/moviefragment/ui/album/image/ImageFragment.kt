package com.eylulcan.moviefragment.ui.album.image

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentImageBinding
import com.eylulcan.moviefragment.util.Utils

class ImageFragment : Fragment() {
     private lateinit var binding:FragmentImageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentImageBinding.bind(view)
        val selectedImageUrl = arguments?.get(getString(R.string.image_url)) as String
        Glide.with(binding.root).load(setImageUrl(selectedImageUrl)).into(binding.artistImageView)

    }

    private fun setImageUrl(profile_path: String?): String {
        return Utils.BASE_IMAGE_URL_ORIGINAL.plus(profile_path)
    }
}