package com.eylulcan.moviefragment.ui.album

import com.eylulcan.moviefragment.domain.entity.ProfileImageEntity

interface ImageListener {
    fun onImageClicked(album: List<ProfileImageEntity>?, position: Int)
}