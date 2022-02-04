package com.eylulcan.moviefragment.domain.entity

data class PopularPeopleListEntity (
    val page: Int,
    val results: List<PeopleResultEntity>,
    val totalPages: Int,
    val totalResults: Int
)