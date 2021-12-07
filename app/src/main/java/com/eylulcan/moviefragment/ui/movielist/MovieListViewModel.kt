package com.eylulcan.moviefragment.ui.movielist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eylulcan.moviefragment.api.MovieAPI
import com.eylulcan.moviefragment.model.GenreList
import com.eylulcan.moviefragment.model.Movie
import com.eylulcan.moviefragment.model.PopularPeopleList
import com.eylulcan.moviefragment.util.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieListViewModel : ViewModel() {

    private var retrofit: MovieAPI? = null
    private val popularMovieList = MutableLiveData<Movie>()
    val popularMovies: LiveData<Movie> get() = popularMovieList
    private val topRatedMovieList = MutableLiveData<Movie>()
    val topRatedMovies: LiveData<Movie> get() = topRatedMovieList

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(Utils.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieAPI::class.java)
    }

    fun getPopularMovieList() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit?.getPopularData()

            withContext(Dispatchers.Main) {
                response?.let {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            popularMovieList.postValue(it)
                        }
                    }
                }
            }
        }
    }

    fun getTopRatedMovieList() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit?.getTopRatedData()

            withContext(Dispatchers.Main) {
                response?.let {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            topRatedMovieList.postValue(it)
                        }
                    }
                }
            }
        }
    }
}