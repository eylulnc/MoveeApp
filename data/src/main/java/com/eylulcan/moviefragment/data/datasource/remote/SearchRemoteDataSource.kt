package com.eylulcan.moviefragment.data.datasource.remote

import com.eylulcan.moviefragment.domain.entity.SearchResultListEntity

interface SearchRemoteDataSource {
    suspend fun getSearchResult(query: String, pageNo: Int): SearchResultListEntity?
}