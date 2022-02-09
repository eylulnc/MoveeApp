package com.eylulcan.moviefragment.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eylulcan.moviefragment.domain.entity.SearchResultListEntity
import com.eylulcan.moviefragment.domain.usecase.search.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchUseCase: SearchUseCase) : ViewModel() {

    private val searchResults = MutableLiveData<SearchResultListEntity>()
    val results: LiveData<SearchResultListEntity> get() = searchResults
    var lastLoadedPage: Int = 1

    fun getSearchResult(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = searchUseCase.invoke(pageNo = lastLoadedPage, query = query)
            response.let {
                searchResults.postValue(it)
            }
        }
    }
}
