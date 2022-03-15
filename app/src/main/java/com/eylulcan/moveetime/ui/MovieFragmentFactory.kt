package com.eylulcan.moveetime.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.eylulcan.moveetime.ui.album.AlbumAdapter
import com.eylulcan.moveetime.ui.album.AlbumFragment
import com.eylulcan.moveetime.ui.album.image.ImageAdapter
import com.eylulcan.moveetime.ui.album.image.ImageFragment
import com.eylulcan.moveetime.ui.artist.ArtistAdapter
import com.eylulcan.moveetime.ui.artist.ArtistFragment
import com.eylulcan.moveetime.ui.discover.DiscoverFragment
import com.eylulcan.moveetime.ui.onboard.OnboardAdapter
import com.eylulcan.moveetime.ui.onboard.OnboardViewPagerFragment
import javax.inject.Inject

open class MovieFragmentFactory @Inject constructor(
    private val onboardAdapter: OnboardAdapter,
    private val albumAdapter: AlbumAdapter,
    private val imageAdapter: ImageAdapter,
    private val artistAdapter: ArtistAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            DiscoverFragment::class.java.name -> DiscoverFragment()
            OnboardViewPagerFragment::class.java.name -> OnboardViewPagerFragment(onboardAdapter)
            AlbumFragment::class.java.name -> AlbumFragment(albumAdapter)
            ImageFragment::class.java.name -> ImageFragment(imageAdapter)
            ArtistFragment::class.java.name -> ArtistFragment(artistAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }
}
