package com.eylulcan.moviefragment.ui.artistdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentArtistDetailBinding
import com.google.android.material.tabs.TabLayout

class ArtistDetailFragment : Fragment() {

    private lateinit var binding: FragmentArtistDetailBinding

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
        val adapter: TabAdapter? = fragmentManager?.let { TabAdapter(it,lifecycle) }
        binding.artistsFragmentViewPager.adapter = adapter

    }


}

