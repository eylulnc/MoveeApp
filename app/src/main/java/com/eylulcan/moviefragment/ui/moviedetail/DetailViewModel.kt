package com.eylulcan.moviefragment.ui.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eylulcan.moviefragment.api.MovieAPI
import com.eylulcan.moviefragment.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private var retrofit: MovieAPI): ViewModel() {

    private val movieCast = MutableLiveData<MovieCredits>()
    val cast: LiveData<MovieCredits> get() = movieCast
    private val movieReviews = MutableLiveData<ReviewList>()
    val reviews: LiveData<ReviewList> get() = movieReviews
    private val movieMore = MutableLiveData<Movie>()
    val more: LiveData<Movie> get() = movieMore
    private val videoList = MutableLiveData<VideoList>()
    val videos: LiveData<VideoList> get() = videoList
    private val movieDetails = MutableLiveData<MovieDetail>()
    val detail: LiveData<MovieDetail> get() = movieDetails

    fun getMovieCast(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.getMovieCredits(movieId = id)
            response.let {
                if (response.isSuccessful) {
                    response.body()?.let {
                        movieCast.postValue(it)
                    }
                }
            }
        }
    }

    fun getReviews(movieId: Int, pageNo: Int = 1) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.getMovieReviews(movieId = movieId, pageNo = pageNo)
            response.let {
                if (response.isSuccessful) {
                    response.body()?.let {
                        movieReviews.postValue(it)
                    }
                }
            }
        }
    }

    fun getMovieMore(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.getMoreMovie(movieId = id)
            response.let {
                if (response.isSuccessful) {
                    response.body()?.let {
                        movieMore.postValue(it)
                    }
                }
            }
        }
    }

    fun getVideoClips(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.getMovieVideoClips(movieId = id)
            response.let {
                if (response.isSuccessful) {
                    response.body()?.let {
                        videoList.postValue(it)
                    }
                }
            }
        }
    }

    fun getMovieDetail(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.getMovieDetail(genreId = id)
            response.let {
                if (response.isSuccessful) {
                    response.body()?.let {
                        movieDetails.postValue(it)
                    }
                }
            }
        }
    }
}
