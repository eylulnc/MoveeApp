package com.eylulcan.moviefragment.ui.artistdetail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabAdapter(manager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(manager, lifecycle) {

    companion object {
        private const val NUM_TABS = 3
    }

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
