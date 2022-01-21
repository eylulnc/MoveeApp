package com.eylulcan.moviefragment.di

import com.eylulcan.moviefragment.ui.album.AlbumFragment
import com.eylulcan.moviefragment.ui.album.ImageListener
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent


@InstallIn(ActivityRetainedComponent::class)
@Module
class ProviderModule {

        @Provides
        fun imageProviderFunction() : ImageListener {
            return AlbumFragment()
        }

}
