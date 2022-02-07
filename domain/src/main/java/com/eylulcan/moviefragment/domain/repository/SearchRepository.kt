package com.eylulcan.moviefragment.domain.repository

import com.eylulcan.moviefragment.domain.entity.SearchResultListEntity

interface SearchRepository {
    suspend fun getSearchResult(query: String, pageNo: Int): SearchResultListEntity?
}