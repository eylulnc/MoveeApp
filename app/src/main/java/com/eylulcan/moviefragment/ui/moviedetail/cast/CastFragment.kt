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
import com.eylulcan.moviefragment.MainActivity
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentCastBinding
import com.eylulcan.moviefragment.ui.artist.ArtistListener
import com.eylulcan.moviefragment.ui.moviedetail.DetailViewModel
import com.eylulcan.moviefragment.util.Utils
import me.samlss.broccoli.Broccoli

private const val SPAN_COUNT = 4

class CastFragment : Fragment(), ArtistListener {

    private lateinit var binding: FragmentCastBinding
    private lateinit var castAdapter: CastAdapter
    private val movieDetailViewModel: DetailViewModel by activityViewModels()
    private var placeholderNeeded = arrayListOf<View>()
    private var broccoli = Broccoli()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_cast, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCastBinding.bind(view)
        setPlaceholders()
        binding.castRecyclerView.layoutManager = GridLayoutManager(context, SPAN_COUNT)
        observeViewModel()

    }

    private fun observeViewModel() {
        movieDetailViewModel.cast.observe(viewLifecycleOwner, { movieCredits ->
            broccoli.removeAllPlaceholders()
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

    private fun setPlaceholders() {
        placeholderNeeded.addAll(arrayListOf(
            binding.castRecyclerView,
        ))
        Utils.addPlaceholders(broccoli = broccoli, placeholderNeeded )

    }
}