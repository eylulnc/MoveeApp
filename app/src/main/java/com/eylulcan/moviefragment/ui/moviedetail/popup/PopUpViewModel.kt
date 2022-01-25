package com.eylulcan.moviefragment.ui.moviedetail.popup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eylulcan.moviefragment.api.MovieAPI
import com.eylulcan.moviefragment.model.PostRatingBody
import com.eylulcan.moviefragment.model.RatingPostResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopUpViewModel @Inject constructor(private var retrofit: MovieAPI): ViewModel() {

    private val ratingResponse = MutableLiveData<RatingPostResponse>()
    val responseRating: LiveData<RatingPostResponse> get() = ratingResponse

    fun postMovieRating(movieID: Int, rate: Double, sessionId: String) {
        val postRatingBody = PostRatingBody()
        postRatingBody.ratingValue = rate
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.postMovieRating(
                movieID = movieID,
                postRatingBody = postRatingBody,
                sessionId = sessionId
            )
            response.let {
                if (response.isSuccessful) {
                    response.body()?.let {
                        ratingResponse.postValue(it)
                    }
                }
            }
        }
    }
}