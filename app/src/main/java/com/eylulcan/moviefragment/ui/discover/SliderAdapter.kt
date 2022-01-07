package com.eylulcan.moviefragment.ui.discover

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

private const val FRAGMENT_COUNT = 5

class SliderAdapter(activity: DiscoverFragment, private val moviesInfo: ArrayList<Pair<Int,String>>) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return FRAGMENT_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SliderFragment(moviesInfo[position])
            1 -> SliderFragment(moviesInfo[position])
            2 -> SliderFragment(moviesInfo[position])
            3 -> SliderFragment(moviesInfo[position])
            4 -> SliderFragment(moviesInfo[position])
            else -> SliderFragment(moviesInfo[position])
        }
    }

}