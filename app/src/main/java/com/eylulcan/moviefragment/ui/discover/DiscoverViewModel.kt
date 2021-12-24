package com.eylulcan.moviefragment.ui.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eylulcan.moviefragment.api.MovieAPI
import com.eylulcan.moviefragment.model.Movie
import com.eylulcan.moviefragment.util.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DiscoverViewModel : ViewModel() {

    private var retrofit: MovieAPI? = null
    private val popularMovieList = MutableLiveData<Movie>()
    val popularMovies: LiveData<Movie> get() = popularMovieList
    private val topRatedMovieList = MutableLiveData<Movie>()
    val topRatedMovies: LiveData<Movie> get() = topRatedMovieList
    private val nowPlayingMovieList = MutableLiveData<Movie>()
    val nowPlaying: LiveData<Movie> get() = nowPlayingMovieList
    var lastLoadedPage: Int = 1

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
                response?.let {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            popularMovieList.postValue(it)
                        }
                }
            }
        }
    }

    fun getTopRatedMovieList() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit?.getTopRatedData()
                response?.let {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            topRatedMovieList.postValue(it)
                        }
                }
            }
        }
    }

    fun getNowPlayingMovieList() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit?.getNowPlayingData(pageNo = lastLoadedPage)
                response?.let {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            nowPlayingMovieList.postValue(it)
                        }
                }
            }
        }
    }
}