package com.eylulcan.moviefragment.domain.entity

class SearchResultListEntity (
    val page: Int,
    val searchResultEntities: List<SearchResultEntity>,
    val totalPages: Int,
    val totalResults: Int
)