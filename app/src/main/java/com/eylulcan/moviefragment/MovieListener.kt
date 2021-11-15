package com.eylulcan.moviefragment;

import android.widget.ImageView
import androidx.navigation.Navigator

interface MovieListener {
    fun onMovieClicked(position: Int , image: ImageView)
}