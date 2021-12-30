package com.eylulcan.moviefragment.util

import android.content.Context
import android.content.res.Configuration

object Utils {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val BASE_IMAGE_URL_300 = "http://image.tmdb.org/t/p/w300"
    const val BASE_IMAGE_URL_ORIGINAL = "http://image.tmdb.org/t/p/original"
    const val BASE_IMAGE_URL_185 = "http://image.tmdb.org/t/p/w185"
    const val API_KEY = "a2d3d4e6888e49e2bcbb7ffe79963274"

     fun isTablet(context: Context): Boolean {
        return ((context.resources.configuration.screenLayout
                and Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE)
    }

}