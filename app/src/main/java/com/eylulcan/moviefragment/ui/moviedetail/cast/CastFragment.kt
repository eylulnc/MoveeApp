package com.eylulcan.moviefragment.ui.moviedetail.cast

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentCastBinding
import com.eylulcan.moviefragment.ui.artist.ArtistListener
import com.eylulcan.moviefragment.ui.moviedetail.DetailViewModel

class CastFragment : Fragment(), ArtistListener {

    private lateinit var binding: FragmentCastBinding
    private lateinit var castAdapter: CastAdapter
    private val movieDetailViewModel: DetailViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cast, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCastBinding.bind(view)
        binding.castRecyclerView.layoutManager = GridLayoutManager(context, 4)
        observeViewModel()

    }

    private fun observeViewModel() {
        movieDetailViewModel.cast.observe(viewLifecycleOwner, { movieCredits ->
            castAdapter = CastAdapter(movieCredits, this)
            binding.castRecyclerView.adapter = castAdapter
        })
    }

    override fun onArtistClicked(id: Int) {
        val artistIdBundle = bundleOf(getString(R.string.artistId) to id)
        this.parentFragment?.parentFragment?.findNavController()?.navigate(
            R.id.action_movieDetailFragment_to_artistDetailFragment, artistIdBundle, null, null
        )
    }
}