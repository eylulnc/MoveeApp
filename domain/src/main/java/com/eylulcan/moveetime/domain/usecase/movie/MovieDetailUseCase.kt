package com.eylulcan.moveetime.domain.usecase.movie

import com.eylulcan.moveetime.domain.entity.MovieDetailEntity
import com.eylulcan.moveetime.domain.repository.MovieRepository
import javax.inject.Inject

class MovieDetailUseCase @Inject constructor(private val repository: MovieRepository) {

    suspend operator fun invoke(movieId: Int): MovieDetailEntity? {
        return repository.getMovieDetail(movieId)

    }
}