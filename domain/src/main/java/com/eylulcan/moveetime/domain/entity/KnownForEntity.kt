package com.eylulcan.moveetime.domain.entity

data class KnownForEntity(
    val adult: Boolean,
    val backdropPath: String,
    val genreIds: List<Int>,
    val id: Int,
    val mediaType: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
    val firstAirDate: String,
    val name: String,
    val originCountry: List<String>,
    val originalName: String
)