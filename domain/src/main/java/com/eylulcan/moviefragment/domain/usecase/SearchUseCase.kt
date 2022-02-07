package com.eylulcan.moviefragment.domain.usecase

import com.eylulcan.moviefragment.domain.entity.SearchResultListEntity
import com.eylulcan.moviefragment.domain.repository.SearchRepository

class SearchUseCase (private val searchRepository: SearchRepository) {
    suspend operator fun invoke(pageNo:Int, query:String): SearchResultListEntity? {
        return searchRepository.getSearchResult(pageNo = pageNo, query = query)
    }
}