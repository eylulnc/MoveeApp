package com.eylulcan.moviefragment.domain.entity

data class SearchResultEntity (
    val id: Int,
    val name: String,
    val posterPath: String,
    val profilePath: String,
    var title: String,
    val mediaType: String
)