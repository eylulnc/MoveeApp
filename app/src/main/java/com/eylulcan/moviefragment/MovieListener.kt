package com.eylulcan.moviefragment

import android.widget.ImageView

interface MovieListener {
    fun onMovieClicked(resultMovie: ResultMovie, image: ImageView, )
}