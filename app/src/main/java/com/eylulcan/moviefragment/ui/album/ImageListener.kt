package com.eylulcan.moviefragment.ui.album

import com.eylulcan.moviefragment.model.ProfileImage

interface ImageListener {
    fun onImageClicked(album: List<ProfileImage>?, position: Int)
}