package com.eylulcan.moviefragment.domain.entity

data class ArtistListEntity(
    val page: Int,
    val results: List<ArtistResultEntity>,
    val totalPages: Int,
    val totalResults: Int
)