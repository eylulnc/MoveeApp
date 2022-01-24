package com.eylulcan.moviefragment

import android.media.Image
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.eylulcan.moviefragment.ui.album.AlbumAdapter
import com.eylulcan.moviefragment.ui.album.AlbumFragment
import com.eylulcan.moviefragment.ui.album.image.ImageAdapter
import com.eylulcan.moviefragment.ui.album.image.ImageFragment
import com.eylulcan.moviefragment.ui.discover.DiscoverFragment
import com.eylulcan.moviefragment.ui.discover.SliderAdapter
import com.eylulcan.moviefragment.ui.onboard.OnboardAdapter
import com.eylulcan.moviefragment.ui.onboard.OnboardViewPagerFragment
import javax.inject.Inject

open class MovieFragmentFactory @Inject constructor(
    private val sliderAdapter: SliderAdapter,
    private val onboardAdapter: OnboardAdapter,
    private val albumAdapter: AlbumAdapter,
    private val imageAdapter: ImageAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            DiscoverFragment::class.java.name -> DiscoverFragment(sliderAdapter)
            OnboardViewPagerFragment::class.java.name -> OnboardViewPagerFragment(onboardAdapter)
            AlbumFragment::class.java.name -> AlbumFragment(albumAdapter)
            ImageFragment::class.java.name -> ImageFragment(imageAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }
}
