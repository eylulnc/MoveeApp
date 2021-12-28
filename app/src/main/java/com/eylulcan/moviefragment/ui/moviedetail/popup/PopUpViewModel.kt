package com.eylulcan.moviefragment.ui.moviedetail.popup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eylulcan.moviefragment.api.MovieAPI
import com.eylulcan.moviefragment.model.PostRatingBody
import com.eylulcan.moviefragment.model.RatingPostResponse
import com.eylulcan.moviefragment.util.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PopUpViewModel : ViewModel() {

    private var retrofit: MovieAPI? = null
    private val ratingResponse = MutableLiveData<RatingPostResponse>()
    val responseRating: LiveData<RatingPostResponse> get() = ratingResponse

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(Utils.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieAPI::class.java)
    }

    fun postMovieRating(movieID: Int, rate: Double, sessionId: String) {
        val postRatingBody = PostRatingBody()
        postRatingBody.ratingValue = rate
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit?.postMovieRating(
                movieID = movieID,
                postRatingBody = postRatingBody,
                sessionId = sessionId
            )
            response?.let {
                if (response.isSuccessful) {
                    response.body()?.let {
                        println("encenc in response ${it.statusMessage}")
                        ratingResponse.postValue(it)
                    }
                }
            }
        }
    }
}