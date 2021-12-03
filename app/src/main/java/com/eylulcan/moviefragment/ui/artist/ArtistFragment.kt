package com.eylulcan.moviefragment.ui.artist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentArtistBinding

class ArtistFragment : Fragment() {

    private val artistViewModel: ArtistViewModel by viewModels()
    private lateinit var binding: FragmentArtistBinding
    private lateinit var artistAdapter: ArtistAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_artist, container, false)
        binding = FragmentArtistBinding.bind(view)
        binding.artistRecyclerView.layoutManager = GridLayoutManager(context, 3)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        artistViewModel.getPopularPeople()
    }

    private fun observeViewModel() {
        artistViewModel.popularPeople.observe(viewLifecycleOwner, { popularPeopleList ->
            artistAdapter = ArtistAdapter(popularPeopleList)
            binding.artistRecyclerView.adapter = artistAdapter
        })
    }
}