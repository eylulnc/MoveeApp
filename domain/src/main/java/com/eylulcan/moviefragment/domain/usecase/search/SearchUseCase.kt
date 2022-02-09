package com.eylulcan.moviefragment.domain.usecase.search

import com.eylulcan.moviefragment.domain.entity.SearchResultListEntity
import com.eylulcan.moviefragment.domain.repository.SearchRepository
import javax.inject.Inject

class SearchUseCase @Inject constructor(private val searchRepository: SearchRepository) {
    suspend operator fun invoke(pageNo: Int, query: String): SearchResultListEntity? {
        return searchRepository.getSearchResult(pageNo = pageNo, query = query)
    }
}