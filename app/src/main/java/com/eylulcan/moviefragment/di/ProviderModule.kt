package com.eylulcan.moviefragment.di

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.api.MovieAPI
import com.eylulcan.moviefragment.ui.discover.DiscoverChildAdapter
import com.eylulcan.moviefragment.ui.discover.SliderAdapter
import com.eylulcan.moviefragment.util.Utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@InstallIn(ActivityComponent::class)
@Module
object ProviderModule {

    @Provides
    fun injectAdapter(@ActivityContext context:Context ):SliderAdapter = SliderAdapter(context as FragmentActivity)

    @Provides
    @Named("Adapter1") fun injectChildAdapter1 ():DiscoverChildAdapter = DiscoverChildAdapter()

    @Provides
    @Named("Adapter2") fun injectChildAdapter2 ():DiscoverChildAdapter = DiscoverChildAdapter()

    @Provides
    @Named("Adapter3") fun injectChildAdapter3 ():DiscoverChildAdapter = DiscoverChildAdapter()
}