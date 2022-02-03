package com.eylulcan.moviefragment.domain.entity

data class ArtistMovieCreditsEntity(
    val castEntity: List<CastEntity>,
    val crewEntity: List<CrewEntity>,
    val id: Int
)