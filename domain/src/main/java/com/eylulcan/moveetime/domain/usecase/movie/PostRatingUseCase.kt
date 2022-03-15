package com.eylulcan.moveetime.domain.usecase.movie

import com.eylulcan.moveetime.domain.entity.PostRatingBodyEntity
import com.eylulcan.moveetime.domain.entity.RatingPostResponseEntity
import com.eylulcan.moveetime.domain.repository.MovieRepository
import javax.inject.Inject

class PostRatingUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    suspend operator fun invoke(
        movieId: Int,
        sessionId: String,
        postBody: PostRatingBodyEntity
    ): RatingPostResponseEntity? {
        return movieRepository.postRateMovie(movieId, sessionId, postBody)
    }
}