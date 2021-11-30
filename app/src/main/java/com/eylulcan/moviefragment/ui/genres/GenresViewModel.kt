package com.eylulcan.moviefragment.ui.genres

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eylulcan.moviefragment.api.MovieAPI
import com.eylulcan.moviefragment.model.GenreList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GenresViewModel : ViewModel() {

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"
    }

    private var retrofit: MovieAPI? = null
    private val genreList = MutableLiveData<GenreList>()
    val genres: LiveData<GenreList> get() = genreList

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieAPI::class.java)
    }

    fun getGenreList() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit?.getGenresData()
            withContext(Dispatchers.Main) {
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
}