package com.eylulcan.moviefragment.domain.entity

data class ReviewListEntity(
    val id: Int,
    val page: Int,
    val results: List<ReviewEntity>,
    val totalPages: Int,
    val totalResults: Int
)