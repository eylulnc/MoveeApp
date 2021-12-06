package com.eylulcan.moviefragment.ui.artistdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentArtistDetailBinding
import com.eylulcan.moviefragment.model.PopularPeopleResult
import com.eylulcan.moviefragment.model.ResultMovie
import com.google.android.material.tabs.TabLayoutMediator

class ArtistDetailFragment : Fragment() {

    private lateinit var binding: FragmentArtistDetailBinding

    private val tabNames = arrayOf(
        "Summary",
        "Movies",
        "More"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_artist_detail, container, false)
        binding = FragmentArtistDetailBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter: TabAdapter? = fragmentManager?.let { TabAdapter(it, lifecycle) }
        binding.artistsFragmentViewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.artistsFragmentViewPager) { tab, position ->
            tab.text = tabNames[position]
        }.attach()

        val selectedPopularPersonID =
            arguments?.get(getString(R.string.artistId)) as Int

    }


}

