package com.eylulcan.moviefragment.ui.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eylulcan.moviefragment.domain.entity.*
import com.eylulcan.moviefragment.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val moreMoviesUseCase: MoreMoviesUseCase,
    private val movieCreditsUseCase: MovieCreditsUseCase,
    private val movieDetailUseCase: MovieDetailUseCase,
    private val movieReviewsUseCase: MovieReviewsUseCase,
    private val videoListUseCase: VideoListUseCase
) : ViewModel() {

    private val movieCast = MutableLiveData<MovieCreditsEntity>()
    val cast: LiveData<MovieCreditsEntity> get() = movieCast
    private val movieReviews = MutableLiveData<ReviewListEntity>()
    val reviews: LiveData<ReviewListEntity> get() = movieReviews
    private val movieMore = MutableLiveData<MovieEntity>()
    val more: LiveData<MovieEntity> get() = movieMore
    private val videoList = MutableLiveData<VideoListEntity>()
    val videos: LiveData<VideoListEntity> get() = videoList
    private val movieDetails = MutableLiveData<MovieDetailEntity>()
    val detailEntity: LiveData<MovieDetailEntity> get() = movieDetails

    fun getMovieCast(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = movieCreditsUseCase.invoke(id)
            response.let {
                movieCast.postValue(it)
            }
        }
    }

    fun getReviews(movieId: Int, pageNo: Int = 1) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = movieReviewsUseCase.invoke(movieId, pageNo)
            response.let {
                movieReviews.postValue(it)
            }
        }
    }

    fun getMovieMore(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = moreMoviesUseCase.invoke(id)
            response.let {
                movieMore.postValue(it)
            }
        }
    }

    fun getVideoClips(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = videoListUseCase.invoke(id)
            response.let {
                videoList.postValue(it)
            }
        }
    }

    fun getMovieDetail(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = movieDetailUseCase.invoke(id)
            response.let {
                movieDetails.postValue(it)
            }
        }
    }
}
