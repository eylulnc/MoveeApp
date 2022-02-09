package com.eylulcan.moviefragment.ui.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eylulcan.moviefragment.domain.entity.GuestSessionEntity
import com.eylulcan.moviefragment.domain.entity.MovieEntity
import com.eylulcan.moviefragment.domain.usecase.movie.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val topRatedMovieUseCase: TopRatedMovieUseCase,
    private val popularMovieUseCase: PopularMovieUseCase,
    private val upcomingDataUseCase: UpcomingDataUseCase,
    private val nowPlayingDataUseCase: NowPlayingDataUseCase,
    private val guestSessionUseCase: GuestSessionUseCase
) : ViewModel() {

    private var popularMovieList = MutableLiveData<MovieEntity>()
    val popularMovies: LiveData<MovieEntity> get() = popularMovieList
    private var topRatedMovieList = MutableLiveData<MovieEntity>()
    val topRatedMovies: LiveData<MovieEntity> get() = topRatedMovieList
    private var nowPlayingMovieList = MutableLiveData<MovieEntity>()
    val nowPlaying: LiveData<MovieEntity> get() = nowPlayingMovieList
    private val userSession = MutableLiveData<GuestSessionEntity>()
    val sessionEntityId: LiveData<GuestSessionEntity> get() = userSession
    private val upcoming = MutableLiveData<MovieEntity>()
    val upcomingMovies: LiveData<MovieEntity> get() = upcoming

    fun getPopularMovieList() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = popularMovieUseCase()
            result.let {
                popularMovieList.postValue(it)
            }
        }
    }

    fun getTopRatedMovieList() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = topRatedMovieUseCase.invoke()
            result.let {
                topRatedMovieList.postValue(it)
            }
        }
    }

    fun getNowPlayingMovieList() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = nowPlayingDataUseCase.invoke()
            result.let {
                nowPlayingMovieList.postValue(it)
            }
        }
    }

    fun getGuestSession() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = guestSessionUseCase.invoke()
            response.let {
                userSession.postValue(it)
            }
        }
    }

    fun getUpcomingMovieList() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = upcomingDataUseCase.invoke()
            response.let {
                upcoming.postValue(it)
            }
        }
    }

    fun setListsToDefault() {
        popularMovieList = MutableLiveData<MovieEntity>()
        topRatedMovieList = MutableLiveData<MovieEntity>()
        nowPlayingMovieList = MutableLiveData<MovieEntity>()
    }

}

