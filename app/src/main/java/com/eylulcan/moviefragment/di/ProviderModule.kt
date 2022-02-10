package com.eylulcan.moviefragment.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.data.service.MovieAPI
import com.eylulcan.moviefragment.domain.util.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object ProviderModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Utils.BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieAPI(retrofit: Retrofit): MovieAPI {
        return retrofit.create(MovieAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideGlide(@ApplicationContext context: Context): RequestManager =
        Glide.with(context).setDefaultRequestOptions(
            RequestOptions().placeholder(R.color.grey_light)
                .error(R.color.grey_light)
        )

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }
}