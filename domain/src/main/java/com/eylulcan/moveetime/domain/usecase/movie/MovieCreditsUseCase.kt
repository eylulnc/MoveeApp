package com.eylulcan.moveetime.domain.usecase.movie

import com.eylulcan.moveetime.domain.entity.MovieCreditsEntity
import com.eylulcan.moveetime.domain.repository.MovieRepository
import javax.inject.Inject

class MovieCreditsUseCase @Inject constructor(private val repository: MovieRepository) {

    suspend operator fun invoke(movieId: Int): MovieCreditsEntity? {
        return repository.getMovieCredits(movieId)

    }
}