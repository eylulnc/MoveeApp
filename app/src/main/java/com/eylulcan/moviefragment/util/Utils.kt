package com.eylulcan.moviefragment.util

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.fragment.app.FragmentActivity
import com.eylulcan.moviefragment.MainActivity
import me.samlss.broccoli.Broccoli
import me.samlss.broccoli.BroccoliGradientDrawable
import me.samlss.broccoli.PlaceholderParameter

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

    fun addPlaceholders(broccoli: Broccoli, itemList: List<View>) {
        itemList.forEach { item ->
            broccoli.addPlaceholder(
                PlaceholderParameter.Builder()
                    .setView(item)
                    .setDrawable(
                        BroccoliGradientDrawable(
                            Color.parseColor("#DDDDDD"),
                            Color.parseColor("#CCCCCC"), 0F, 1000, LinearInterpolator()
                        )
                    )
                    .build()
            )
            broccoli.show()
        }
    }

}