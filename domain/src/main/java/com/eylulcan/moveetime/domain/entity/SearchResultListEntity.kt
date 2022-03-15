package com.eylulcan.moveetime.domain.entity

class SearchResultListEntity(
    val page: Int,
    val searchResults: List<SearchResultEntity>,
    val totalPages: Int,
    val totalResults: Int
)