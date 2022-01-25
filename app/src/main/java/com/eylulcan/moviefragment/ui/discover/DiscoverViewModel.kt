package com.eylulcan.moviefragment.ui.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eylulcan.moviefragment.api.MovieAPI
import com.eylulcan.moviefragment.model.GuestSession
import com.eylulcan.moviefragment.model.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.hilt.lifecycle.ViewModelInject
import com.eylulcan.moviefragment.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@HiltViewModel
class DiscoverViewModel @Inject constructor(private var retrofit: MovieAPI) : ViewModel() {

    private var popularMovieList = MutableLiveData<Movie>()
    val popularMovies: LiveData<Movie> get() = popularMovieList
    private var topRatedMovieList = MutableLiveData<Movie>()
    val topRatedMovies: LiveData<Movie> get() = topRatedMovieList
    private var nowPlayingMovieList = MutableLiveData<Movie>()
    val nowPlaying: LiveData<Movie> get() = nowPlayingMovieList
    private val userSession = MutableLiveData<GuestSession>()
    val sessionId: LiveData<GuestSession> get() = userSession
    private val upcoming = MutableLiveData<Movie>()
    val upcomingMovies: LiveData<Movie> get() = upcoming

    fun getPopularMovieList() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.getPopularData()
            response.let {
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
            val response = retrofit.getTopRatedData()
            response.let {
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
            val response = retrofit.getNowPlayingData()
            response.let {
                if (response.isSuccessful) {
                    response.body()?.let {
                        nowPlayingMovieList.postValue(it)
                    }
                }
            }
        }
    }

    fun getGuestSession() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.getGuestSessionId()
            response.let {
                if (response.isSuccessful) {
                    response.body()?.let {
                        userSession.postValue(it)
                    }
                }
            }
        }
    }

    fun getUpcomingMovieList() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.getUpcomingData()
            response.let {
                if (response.isSuccessful) {
                    response.body()?.let {
                        upcoming.postValue(it)
                    }
                }
            }
        }
    }

    fun setListsToDefault(){
        popularMovieList = MutableLiveData<Movie>()
        topRatedMovieList = MutableLiveData<Movie>()
        nowPlayingMovieList = MutableLiveData<Movie>()
    }

}