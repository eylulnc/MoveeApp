package com.eylulcan.moveetime.domain.entity

data class ArtistMovieCreditsEntity(
    val cast: List<CastEntity>,
    val crew: List<CrewEntity>,
    val id: Int
)