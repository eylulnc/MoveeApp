package com.eylulcan.moviefragment.ui.artist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eylulcan.moviefragment.api.MovieAPI
import com.eylulcan.moviefragment.model.PopularPeopleList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ArtistViewModel : ViewModel() {

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"
    }

    private var retrofit: MovieAPI? = null
    private val popularPeopleList = MutableLiveData<PopularPeopleList>()
    val popularPeople: LiveData<PopularPeopleList> get() = popularPeopleList

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieAPI::class.java)
    }

    fun getPopularPeople() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit?.getPopularPeople()
            withContext(Dispatchers.Main) {
                response?.let {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            popularPeopleList.postValue(it)
                        }
                    }
                }
            }
        }
    }
}