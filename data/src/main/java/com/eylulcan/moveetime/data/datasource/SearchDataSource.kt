package com.eylulcan.moveetime.data.datasource

import com.eylulcan.moveetime.data.datasource.remote.SearchRemoteDataSource
import com.eylulcan.moveetime.data.mapper.SearchMapper
import com.eylulcan.moveetime.data.service.MovieAPI
import com.eylulcan.moveetime.domain.entity.SearchResultListEntity
import javax.inject.Inject

class SearchDataSource @Inject constructor(
    private val api: MovieAPI,
    private val searchMapper: SearchMapper
) : SearchRemoteDataSource {

    override suspend fun getSearchResult(query: String, pageNo: Int): SearchResultListEntity? {
        return api.getSearchResult(query = query, pageNo = pageNo).body()
            ?.let { searchMapper.convertSearchResultListEntity(it) }
    }
}