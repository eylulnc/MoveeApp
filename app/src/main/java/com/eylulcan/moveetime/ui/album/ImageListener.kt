package com.eylulcan.moveetime.ui.album

import com.eylulcan.moveetime.domain.entity.ProfileImageEntity

interface ImageListener {
    fun onImageClicked(album: List<ProfileImageEntity>?, position: Int)
}