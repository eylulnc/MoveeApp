package com.eylulcan.moviefragment.ui.discover

import androidx.recyclerview.widget.RecyclerView
import com.eylulcan.moviefragment.model.ResultMovie

interface MovieListener {
    fun onMovieClicked(resultMovie: ResultMovie)
}