package com.eylulcan.moviefragment.data.repository

import com.eylulcan.moviefragment.data.datasource.remote.SearchRemoteDataSource
import com.eylulcan.moviefragment.domain.entity.SearchResultListEntity
import com.eylulcan.moviefragment.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val dataSource: SearchRemoteDataSource) :
    SearchRepository {
    override suspend fun getSearchResult(query: String, pageNo: Int): SearchResultListEntity? {
        return dataSource.getSearchResult(query = query, pageNo = pageNo)
    }
}