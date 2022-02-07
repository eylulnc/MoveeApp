package com.eylulcan.moviefragment.domain.usecase

import com.eylulcan.moviefragment.domain.entity.MovieCreditsEntity
import com.eylulcan.moviefragment.domain.repository.MovieRepository

class MovieCreditsUseCase (private val repository: MovieRepository) {

    suspend operator fun invoke(movieId: Int): MovieCreditsEntity? {
        return repository.getMovieCredits(movieId)

    }
}