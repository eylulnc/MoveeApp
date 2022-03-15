package com.eylulcan.moveetime.domain.entity

import java.io.Serializable

data class MovieEntity(
    val page: Int,
    val results: List<ResultMovieEntity>,
    val totalPages: Int,
    val totalResults: Int
) : Serializable