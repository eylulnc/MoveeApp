package com.eylulcan.moviefragment.domain.entity

data class ReviewEntity(
    val author: String,
    val authorDetails: AuthorDetailsEntity,
    val content: String,
    val createdAt: String,
    val id: String,
    val updatedAt: String,
    val url: String
)