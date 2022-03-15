package com.eylulcan.moveetime.ui.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.eylulcan.moveetime.R
import com.eylulcan.moveetime.databinding.FragmentAlbumBinding
import com.eylulcan.moveetime.domain.entity.ArtistAlbumEntity
import com.eylulcan.moveetime.domain.entity.ProfileImageEntity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val SPAN_COUNT = 3

@AndroidEntryPoint
class AlbumFragment @Inject constructor(private val albumAdapter: AlbumAdapter) : Fragment(),
    ImageListener {

    private var _binding: FragmentAlbumBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.albumRecyclerView.layoutManager = GridLayoutManager(requireContext().applicationContext, SPAN_COUNT)
        val selectedArtistAlbum =
            arguments?.get(getString(R.string.photo_album)) as ArtistAlbumEntity
        val artistAlbum = selectedArtistAlbum.artistProfileImages
        binding.albumRecyclerView.adapter = albumAdapter
        albumAdapter.setOnItemClickListener { album, position ->
            onImageClicked(album, position)
        }
        albumAdapter.album = artistAlbum
    }

    override fun onImageClicked(album: List<ProfileImageEntity>?, position: Int) {
        val albumPositionPair: Pair<List<ProfileImageEntity>?, Int> = Pair(album, position)
        val imageAlbumDataBundle = bundleOf((getString(R.string.image_url) to albumPositionPair))
        findNavController().navigate(
            R.id.action_albumFragment_to_imageFragment,
            imageAlbumDataBundle
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}