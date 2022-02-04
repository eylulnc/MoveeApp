package com.eylulcan.moviefragment.domain.entity

data class VideoListEntity (
    private val id: Int,
    val results: List<VideoResultEntity>
)