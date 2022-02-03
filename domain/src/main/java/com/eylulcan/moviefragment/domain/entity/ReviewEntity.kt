package com.eylulcan.moviefragment.domain.entity

data class ReviewEntity (
    val author: String,
    val authorDetailsEntity: AuthorDetailsEntity,
    val content: String,
    val createdAt: String,
    val id: String,
    val updatedAt: String,
    val url: String
)