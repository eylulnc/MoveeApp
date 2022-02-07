package com.eylulcan.moviefragment.data.datasource

import com.eylulcan.moviefragment.data.mapper.SearchMapper
import com.eylulcan.moviefragment.data.service.MovieAPI
import com.eylulcan.moviefragment.domain.entity.SearchResultListEntity
import com.eylulcan.moviefragment.domain.repository.SearchRepository
import javax.inject.Inject

class SearchDataSource @Inject constructor(
    private val api: MovieAPI,
    private val searchMapper: SearchMapper
) : SearchRepository{

    override suspend fun getSearchResult(query: String, pageNo: Int): SearchResultListEntity? {
        return api.getSearchResult(query = query, pageNo = pageNo).body()?.let { searchMapper.convertSearchResultListEntity(it) }
    }
}