package com.eylulcan.moveetime.ui.album.image

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.eylulcan.moveetime.R
import com.eylulcan.moveetime.databinding.FragmentImageBinding
import com.eylulcan.moveetime.domain.entity.ProfileImageEntity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ImageFragment @Inject constructor(private val imageAdapter: ImageAdapter) : Fragment() {

    private  var _binding: FragmentImageBinding? = null
    private val binding get() =  _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    @Suppress("UNCHECKED_CAST")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageFragmentRecyclerView.layoutManager =
            LinearLayoutManager(requireContext().applicationContext, LinearLayoutManager.HORIZONTAL, false)
        val albumInformation = arguments?.get(getString(R.string.image_url)) as Pair<*, *>
        val album = albumInformation.first as List<ProfileImageEntity>?
        val position = albumInformation.second as Int
        binding.imageFragmentRecyclerView.adapter = imageAdapter
        imageAdapter.album = album ?: emptyList()
        binding.imageFragmentRecyclerView.scrollToPosition(position)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
