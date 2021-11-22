package com.eylulcan.moviefragment

import android.widget.ImageView

interface MovieListener {
    fun onMovieClicked(position: Int , image: ImageView)
}