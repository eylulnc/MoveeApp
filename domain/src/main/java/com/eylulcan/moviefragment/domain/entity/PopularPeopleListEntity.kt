package com.eylulcan.moviefragment.domain.entity

data class PopularPeopleListEntity (
    val page: Int,
    val resultEntities: List<PeopleResultEntity>,
    val totalPages: Int,
    val totalResults: Int
)