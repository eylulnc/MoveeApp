package com.eylulcan.moviefragment.di

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.RequestManager
import com.eylulcan.moviefragment.ui.discover.DiscoverChildAdapter
import com.eylulcan.moviefragment.ui.discover.SliderAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Named

@InstallIn(ActivityComponent::class)
@Module
object AdapterProviderModule {

    @Provides
    fun injectAdapter(@ActivityContext context:Context, glide: RequestManager):SliderAdapter = SliderAdapter(context as FragmentActivity, glide)

    @Provides
    @Named("Adapter1") fun injectChildAdapter1 (glide:RequestManager):DiscoverChildAdapter = DiscoverChildAdapter(glide)

    @Provides
    @Named("Adapter2") fun injectChildAdapter2 (glide:RequestManager):DiscoverChildAdapter = DiscoverChildAdapter(glide)

    @Provides
    @Named("Adapter3") fun injectChildAdapter3 (glide:RequestManager):DiscoverChildAdapter = DiscoverChildAdapter(glide)
}