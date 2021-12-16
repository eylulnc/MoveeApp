package com.eylulcan.moviefragment.ui.search

import android.widget.ImageView

interface SearchListener {
    fun onMovieClicked(id: Int, image: ImageView)
    fun onPersonClicked(id: Int)
}