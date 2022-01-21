package com.eylulcan.moviefragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.eylulcan.moviefragment.ui.discover.DiscoverFragment
import com.eylulcan.moviefragment.ui.discover.SliderAdapter
import com.eylulcan.moviefragment.ui.onboard.OnboardAdapter
import com.eylulcan.moviefragment.ui.onboard.OnboardViewPagerFragment
import javax.inject.Inject

open class MovieFragmentFactory @Inject constructor(
    private val sliderAdapter: SliderAdapter,
    private val onboardAdapter: OnboardAdapter,
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            DiscoverFragment::class.java.name -> DiscoverFragment(sliderAdapter)
            OnboardViewPagerFragment::class.java.name -> OnboardViewPagerFragment(onboardAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }
}
