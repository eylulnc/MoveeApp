package com.eylulcan.moviefragment.domain.entity

data class ProfileImageEntity(
    val aspectRatio: Double,
    val height: Int,
    val iso6391: Int,
    val filePath: String,
    val voteAverage: Double,
    val voteCount: Int,
    val width: Int
)