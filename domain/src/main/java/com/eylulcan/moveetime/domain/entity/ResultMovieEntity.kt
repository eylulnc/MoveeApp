package com.eylulcan.moveetime.domain.entity

import java.io.Serializable

data class ResultMovieEntity(
    val adult: Boolean,
    val backdropPath: String,
    val genreIds: List<Int>,
    val id: Int? = null,
    val originalLanguage: String? = null,
    val originalTitle: String? = null,
    val overview: String? = null,
    val popularity: String? = null,
    val posterPath: String? = null,
    val releaseDate: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    val voteAverage: Double? = null,
    val voteCount: Int? = null
) : Serializable