package com.eylulcan.moviefragment.ui.artistdetail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.eylulcan.moviefragment.ui.artistdetail.artistmovie.ArtistMoviesFragment
import com.eylulcan.moviefragment.ui.artistdetail.summary.SummaryFragment

private const val NUM_TABS = 3

class TabAdapter(manager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(manager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SummaryFragment()
            1 -> ArtistMoviesFragment()
            2 -> SummaryFragment()
            else -> SummaryFragment()
        }
    }
}
