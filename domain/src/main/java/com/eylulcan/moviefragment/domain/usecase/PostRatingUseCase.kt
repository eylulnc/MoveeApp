package com.eylulcan.moviefragment.domain.usecase

import com.eylulcan.moviefragment.domain.entity.PostRatingBodyEntity
import com.eylulcan.moviefragment.domain.entity.RatingPostResponseEntity
import com.eylulcan.moviefragment.domain.repository.MovieRepository

class PostRatingUseCase(private val movieRepository: MovieRepository) {
    suspend operator fun invoke(movieId:Int, sessionId:String, postBody: PostRatingBodyEntity): RatingPostResponseEntity? {
        return movieRepository.postRateMovie(movieId,sessionId,postBody)
    }
}