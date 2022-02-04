package com.eylulcan.moviefragment.domain.entity

data class ArtistMovieCreditsEntity(
    val cast: List<CastEntity>,
    val crew: List<CrewEntity>,
    val id: Int
)