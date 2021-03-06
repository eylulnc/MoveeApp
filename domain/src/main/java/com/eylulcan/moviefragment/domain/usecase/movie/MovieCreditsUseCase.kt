package com.eylulcan.moviefragment.domain.usecase.movie

import com.eylulcan.moviefragment.domain.entity.MovieCreditsEntity
import com.eylulcan.moviefragment.domain.repository.MovieRepository
import javax.inject.Inject

class MovieCreditsUseCase @Inject constructor(private val repository: MovieRepository) {

    suspend operator fun invoke(movieId: Int): MovieCreditsEntity? {
        return repository.getMovieCredits(movieId)

    }
}