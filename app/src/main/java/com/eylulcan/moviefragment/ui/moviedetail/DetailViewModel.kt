package com.eylulcan.moviefragment.ui.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eylulcan.moviefragment.api.MovieAPI
import com.eylulcan.moviefragment.model.Movie
import com.eylulcan.moviefragment.model.MovieDetail
import com.eylulcan.moviefragment.model.MovieCredits
import com.eylulcan.moviefragment.model.ReviewList
import com.eylulcan.moviefragment.model.VideoList
import com.eylulcan.moviefragment.util.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailViewModel : ViewModel() {

    private var retrofit: MovieAPI? = null
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

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(Utils.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieAPI::class.java)
    }

    fun getMovieCast(id:Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit?.getMovieCredits(id)
            withContext(Dispatchers.Main) {
                response?.let {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            movieCast.postValue(it)
                        }
                    }
                }
            }
        }
    }

    fun getReviews(id:Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit?.getMovieReviews(id)
            withContext(Dispatchers.Main) {
                response?.let {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            movieReviews.postValue(it)
                        }
                    }
                }
            }
        }
    }

    fun getMovieMore(id:Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit?.getMoreMovie(id)
            withContext(Dispatchers.Main) {
                response?.let {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            movieMore.postValue(it)
                        }
                    }
                }
            }
        }
    }

    fun getVideoClips(id:Int){
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit?.getMovieVideoClips(id)
            withContext(Dispatchers.Main) {
                response?.let {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            videoList.postValue(it)
                        }
                    }
                }
            }
        }
    }

    fun getMovieDetail(id:Int){
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit?.getMovieDetail(id)
            withContext(Dispatchers.Main) {
                response?.let {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            movieDetails.postValue(it)
                        }
                    }
                }
            }
        }
    }
}
