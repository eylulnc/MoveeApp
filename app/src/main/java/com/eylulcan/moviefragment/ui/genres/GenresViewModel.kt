package com.eylulcan.moviefragment.ui.genres

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eylulcan.moviefragment.api.MovieAPI
import com.eylulcan.moviefragment.model.GenreList
import com.eylulcan.moviefragment.util.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class GenresViewModel @Inject constructor(): ViewModel() {

    private var retrofit: MovieAPI? = null
    private val genreList = MutableLiveData<GenreList>()
    val genres: LiveData<GenreList> get() = genreList

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(Utils.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieAPI::class.java)
    }

    fun getGenreList() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit?.getGenresData()
            response?.let {
                if (response.isSuccessful) {
                    response.body()?.let {
                        genreList.postValue(it)
                    }
                }
            }
        }
    }
}