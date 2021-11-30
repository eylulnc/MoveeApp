package com.eylulcan.moviefragment.ui.movielist

import android.widget.ImageView
import com.eylulcan.moviefragment.model.ResultMovie

interface MovieListener {
    fun onMovieClicked(resultMovie: ResultMovie, image: ImageView)
}