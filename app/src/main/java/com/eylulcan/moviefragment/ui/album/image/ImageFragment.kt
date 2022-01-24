package com.eylulcan.moviefragment.ui.album.image

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentImageBinding
import com.eylulcan.moviefragment.model.ProfileImage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ImageFragment @Inject constructor(private val imageAdapter:ImageAdapter): Fragment() {

    private lateinit var binding: FragmentImageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_image, container, false)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentImageBinding.bind(view)
        binding.imageFragmentRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val albumInformation = arguments?.get(getString(R.string.image_url)) as Pair<*, *>
        val album = albumInformation.first as List<ProfileImage>?
        val position = albumInformation.second as Int
        binding.imageFragmentRecyclerView.adapter = imageAdapter
        imageAdapter.album = album ?: emptyList()
        binding.imageFragmentRecyclerView.scrollToPosition(position)

    }
}
