package com.eylulcan.moviefragment.ui.album

import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentAlbumBinding
import com.eylulcan.moviefragment.model.ArtistAlbum

class AlbumFragment : Fragment() , ImageListener {

    private lateinit var binding: FragmentAlbumBinding
    private lateinit var albumAdapter: AlbumAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_album, container, false)
        binding = FragmentAlbumBinding.bind(view)
        binding.albumRecyclerView.layoutManager = GridLayoutManager(context,4)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val selectedArtistAlbum =  arguments?.get(getString(R.string.photo_album)) as ArtistAlbum
        albumAdapter = AlbumAdapter(selectedArtistAlbum,this)
        binding.albumRecyclerView.adapter = albumAdapter
    }

    override fun onImageClicked(image: String) {
        val imageUrlDataBundle = bundleOf((getString(R.string.image_url) to image))
        findNavController().navigate(R.id.action_albumFragment_to_imageFragment, imageUrlDataBundle)
    }
}