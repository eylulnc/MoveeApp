package com.eylulcan.moviefragment.domain.usecase

import com.eylulcan.moviefragment.domain.entity.ReviewListEntity
import com.eylulcan.moviefragment.domain.repository.MovieRepository

class MovieReviewsUseCase (private val repository: MovieRepository) {

    suspend operator fun invoke(movieId: Int, pageNo: Int): ReviewListEntity? {
        return repository.getMovieReviews(movieId,pageNo)

    }
}