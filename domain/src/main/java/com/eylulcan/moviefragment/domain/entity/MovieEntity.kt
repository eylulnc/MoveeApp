package com.eylulcan.moviefragment.domain.entity

import java.io.Serializable

data class MovieEntity(
    val page: Int,
    val resultEntities: List<ResultMovieEntity>,
    val totalPages: Int,
    val totalResults: Int
) : Serializable