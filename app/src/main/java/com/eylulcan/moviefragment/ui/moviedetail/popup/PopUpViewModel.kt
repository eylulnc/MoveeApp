package com.eylulcan.moviefragment.ui.moviedetail.popup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eylulcan.moviefragment.domain.entity.PostRatingBodyEntity
import com.eylulcan.moviefragment.domain.entity.RatingPostResponseEntity
import com.eylulcan.moviefragment.domain.usecase.movie.PostRatingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopUpViewModel @Inject constructor(private val postRatingUseCase: PostRatingUseCase) :
    ViewModel() {

    private val ratingResponse = MutableLiveData<RatingPostResponseEntity>()
    val responseRating: LiveData<RatingPostResponseEntity> get() = ratingResponse

    fun postMovieRating(movieID: Int, rate: Double, sessionId: String) {
        val postRatingBody = PostRatingBodyEntity(rate)
        CoroutineScope(Dispatchers.IO).launch {
            val response = postRatingUseCase.invoke(
                movieId = movieID,
                sessionId = sessionId,
                postBody = postRatingBody
            )
            response.let {
                ratingResponse.postValue(it)
            }
        }
    }
}
