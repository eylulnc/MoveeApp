package com.eylulcan.moviefragment.data.mapper

import com.eylulcan.moviefragment.data.Utils.IF_INT_NULL
import com.eylulcan.moviefragment.data.Utils.IF_STR_NULL
import com.eylulcan.moviefragment.data.model.SearchResult
import com.eylulcan.moviefragment.data.model.SearchResultList
import com.eylulcan.moviefragment.domain.entity.SearchResultEntity
import com.eylulcan.moviefragment.domain.entity.SearchResultListEntity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchMapper @Inject constructor() {
    fun convertSearchResultListEntity(searchResultList: SearchResultList): SearchResultListEntity {
        val searchResults = arrayListOf<SearchResultEntity>()
        searchResultList.searchResults?.forEach {
            searchResults.add(convertToSearchResultEntity(it))
        }
        return SearchResultListEntity(
            page = searchResultList.page ?: IF_INT_NULL,
            searchResults = searchResults,
            totalPages = searchResultList.totalPages ?: IF_INT_NULL,
            totalResults = searchResultList.totalResults ?: IF_INT_NULL
        )
    }

    private fun convertToSearchResultEntity(searchResult: SearchResult): SearchResultEntity {
        return SearchResultEntity(
            id = searchResult.id ?: IF_INT_NULL,
            name = searchResult.name ?: IF_STR_NULL,
            posterPath = searchResult.posterPath ?: IF_STR_NULL,
            profilePath = searchResult.profilePath ?: IF_STR_NULL,
            title = searchResult.title ?: IF_STR_NULL,
            mediaType = searchResult.mediaType ?: IF_STR_NULL
        )
    }
}