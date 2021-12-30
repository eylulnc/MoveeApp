package com.eylulcan.moviefragment.ui.discover

import com.eylulcan.moviefragment.model.ResultMovie

interface MovieListener {
    fun onMovieClicked(resultMovie: ResultMovie)
}