package com.eylulcan.moviefragment.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eylulcan.moviefragment.api.MovieAPI
import com.eylulcan.moviefragment.model.MovieDetail
import com.eylulcan.moviefragment.model.SearchResult
import com.eylulcan.moviefragment.model.SearchResultList
import com.eylulcan.moviefragment.util.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchViewModel : ViewModel() {

    private var retrofit: MovieAPI? = null
    private val searchResults = MutableLiveData<SearchResultList>()
    val result: LiveData<SearchResultList> get() = searchResults

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(Utils.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieAPI::class.java)
    }

    fun getSearchResult(query: String, pageNo: Int = 1) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit?.getSearchResult(query = query, pageNo = pageNo)
            withContext(Dispatchers.Main) {
                response?.let {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            searchResults.postValue(it)
                        }
                    }
                }
            }
        }
    }

}