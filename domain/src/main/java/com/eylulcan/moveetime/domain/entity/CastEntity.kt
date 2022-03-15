package com.eylulcan.moveetime.domain.entity

data class CastEntity(
    val id: Int,
    val originalTitle: String,
    val posterPath: String,
    val video: Boolean,
    val voteAverage: Double,
    val overview: String,
    val releaseDate: String,
    val voteCount: Int,
    val title: String,
    val adult: Boolean,
    val backdropPath: String,
    val originalLanguage: String,
    val genreIds: List<Int>,
    val popularity: Double,
    val character: String,
    val creditId: String,
    val order: Int
)