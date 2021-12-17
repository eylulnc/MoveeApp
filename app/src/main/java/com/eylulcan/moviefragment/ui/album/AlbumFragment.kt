package com.eylulcan.moviefragment.ui.album

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentAlbumBinding
import com.eylulcan.moviefragment.model.ArtistAlbum
import com.eylulcan.moviefragment.model.ProfileImage

private const val SPAN_COUNT = 4

class AlbumFragment : Fragment(), ImageListener {

    private lateinit var binding: FragmentAlbumBinding
    private lateinit var albumAdapter: AlbumAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_album, container, false)
        binding = FragmentAlbumBinding.bind(view)
        binding.albumRecyclerView.layoutManager = GridLayoutManager(context, SPAN_COUNT)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val selectedArtistAlbum = arguments?.get(getString(R.string.photo_album)) as ArtistAlbum
        albumAdapter = AlbumAdapter(selectedArtistAlbum, this)
        binding.albumRecyclerView.adapter = albumAdapter
    }

    override fun onImageClicked(album: List<ProfileImage>?, position: Int) {
        val albumPositionPair: Pair<List<ProfileImage>?, Int> = Pair(album, position)
        val imageAlbumDataBundle = bundleOf((getString(R.string.image_url) to albumPositionPair))
        findNavController().navigate(
            R.id.action_albumFragment_to_imageFragment,
            imageAlbumDataBundle
        )
    }
}