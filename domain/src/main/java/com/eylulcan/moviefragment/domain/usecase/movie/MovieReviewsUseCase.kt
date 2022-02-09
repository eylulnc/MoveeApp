package com.eylulcan.moviefragment.domain.usecase.movie

import com.eylulcan.moviefragment.domain.entity.ReviewListEntity
import com.eylulcan.moviefragment.domain.repository.MovieRepository
import javax.inject.Inject

class MovieReviewsUseCase @Inject constructor(private val repository: MovieRepository) {

    suspend operator fun invoke(movieId: Int, pageNo: Int): ReviewListEntity? {
        return repository.getMovieReviews(movieId, pageNo)

    }
}