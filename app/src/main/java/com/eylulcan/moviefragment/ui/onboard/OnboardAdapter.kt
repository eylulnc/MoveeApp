package com.eylulcan.moviefragment.ui.onboard

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.eylulcan.moviefragment.R

private const val FRAGMENT_COUNT = 3

class OnboardAdapter(activity: OnboardViewPagerFragment) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return FRAGMENT_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OnboardFragment(R.drawable.onboard1)
            1 -> OnboardFragment(R.drawable.onboard2)
            2 -> OnboardFragment(R.drawable.onboard3)
            else -> OnboardFragment(R.drawable.onboard1)
        }
    }
}