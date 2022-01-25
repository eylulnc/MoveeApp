package com.eylulcan.moviefragment.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eylulcan.moviefragment.api.MovieAPI
import com.eylulcan.moviefragment.model.SearchResultList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private var retrofit: MovieAPI): ViewModel() {

    private val searchResults = MutableLiveData<SearchResultList>()
    val result: LiveData<SearchResultList> get() = searchResults
    var lastLoadedPage: Int = 1

    fun getSearchResult(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.getSearchResult(query = query, pageNo = lastLoadedPage)
            response.let {
                if (response.isSuccessful) {
                    response.body()?.let {
                        searchResults.postValue(it)
                    }
                }
            }
        }
    }

}