package com.eylulcan.moveetime.domain.entity

import java.io.Serializable

data class ArtistAlbumEntity(
    val id: Int,
    val artistProfileImages: List<ProfileImageEntity>
) : Serializable