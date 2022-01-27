package com.eylulcan.moviefragment.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.eylulcan.moviefragment.ui.album.AlbumAdapter
import com.eylulcan.moviefragment.ui.album.AlbumFragment
import com.eylulcan.moviefragment.ui.album.image.ImageAdapter
import com.eylulcan.moviefragment.ui.album.image.ImageFragment
import com.eylulcan.moviefragment.ui.artist.ArtistAdapter
import com.eylulcan.moviefragment.ui.artist.ArtistFragment
import com.eylulcan.moviefragment.ui.discover.DiscoverFragment
import com.eylulcan.moviefragment.ui.onboard.OnboardAdapter
import com.eylulcan.moviefragment.ui.onboard.OnboardViewPagerFragment
import javax.inject.Inject

open class MovieFragmentFactory @Inject constructor(
    private val onboardAdapter: OnboardAdapter,
    private val albumAdapter: AlbumAdapter,
    private val imageAdapter: ImageAdapter,
    private val artistAdapter: ArtistAdapter,
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
