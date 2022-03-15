package com.eylulcan.moveetime.data.datasource.remote

import com.eylulcan.moveetime.domain.entity.SearchResultListEntity

interface SearchRemoteDataSource {
    suspend fun getSearchResult(query: String, pageNo: Int): SearchResultListEntity?
}