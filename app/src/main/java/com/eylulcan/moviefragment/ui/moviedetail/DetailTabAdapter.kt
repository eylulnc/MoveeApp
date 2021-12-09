package com.eylulcan.moviefragment.ui.moviedetail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.eylulcan.moviefragment.ui.moviedetail.cast.CastFragment
import com.eylulcan.moviefragment.ui.moviedetail.more.MoreFragment
import com.eylulcan.moviefragment.ui.moviedetail.reviews.ReviewsFragment

class DetailTabAdapter(manager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(manager, lifecycle) {

    companion object {
        private const val NUM_TABS = 3
    }

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CastFragment()
            1 -> ReviewsFragment()
            2 -> MoreFragment()
            else -> CastFragment()
        }
    }
}