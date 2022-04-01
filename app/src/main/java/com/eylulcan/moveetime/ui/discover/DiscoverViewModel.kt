package com.eylulcan.moveetime.ui.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eylulcan.moveetime.domain.entity.GuestSessionEntity
import com.eylulcan.moveetime.domain.entity.MovieEntity
import com.eylulcan.moveetime.domain.util.ResultData
import com.eylulcan.moveetime.domain.usecase.auth.SignOutUseCase
import com.eylulcan.moveetime.domain.usecase.movie.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val topRatedMovieUseCase: TopRatedMovieUseCase,
    private val popularMovieUseCase: PopularMovieUseCase,
    private val upcomingDataUseCase: UpcomingDataUseCase,
    private val nowPlayingDataUseCase: NowPlayingDataUseCase,
    private val guestSessionUseCase: GuestSessionUseCase,
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {

    private var popularMovieList = MutableLiveData<MovieEntity>()
    val popularMovies: LiveData<MovieEntity> get() = popularMovieList
    private var topRatedMovieList = MutableLiveData<MovieEntity>()
    val topRatedMovies: LiveData<MovieEntity> get() = topRatedMovieList
    private var nowPlayingMovieList = MutableLiveData<MovieEntity>()
    val nowPlaying: LiveData<MovieEntity> get() = nowPlayingMovieList
    private val userSession = MutableLiveData<GuestSessionEntity>()
    val sessionEntityId: LiveData<GuestSessionEntity> get() = userSession
    private var upcoming = MutableLiveData<MovieEntity>()
    val upcomingMovies: LiveData<MovieEntity> get() = upcoming
    private val signOutResult = MutableLiveData<ResultData<Unit>>()
    val signOut: LiveData<ResultData<Unit>> get() = signOutResult

    fun getPopularMovieList() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = popularMovieUseCase()
            result?.let {
                popularMovieList.postValue(it)
            }
        }
    }

    fun getTopRatedMovieList() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = topRatedMovieUseCase.invoke()
            result?.let {
                topRatedMovieList.postValue(it)
            }
        }
    }

    fun getNowPlayingMovieList() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = nowPlayingDataUseCase.invoke()
            result?.let {
                nowPlayingMovieList.postValue(it)
            }
        }
    }

    fun getGuestSession() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = guestSessionUseCase.invoke()
            response?.let {
                userSession.postValue(it)
            }
        }
    }

    fun getUpcomingMovieList() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = upcomingDataUseCase.invoke()
            response?.let {
                upcoming.postValue(it)
            }
        }
    }

    fun signOut() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = signOutUseCase.invoke()
            response.collect {
                signOutResult.postValue(it)
            }
        }
    }

    fun setListsToDefault() {
        popularMovieList = MutableLiveData<MovieEntity>()
        topRatedMovieList = MutableLiveData<MovieEntity>()
        nowPlayingMovieList = MutableLiveData<MovieEntity>()
        upcoming = MutableLiveData<MovieEntity>()
    }

}

