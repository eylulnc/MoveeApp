package com.eylulcan.moveetime.domain.repository

import com.eylulcan.moveetime.domain.entity.SearchResultListEntity

interface SearchRepository {
    suspend fun getSearchResult(query: String, pageNo: Int): SearchResultListEntity?
}