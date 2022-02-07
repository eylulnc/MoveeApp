package com.eylulcan.moviefragment.domain.usecase

import com.eylulcan.moviefragment.domain.entity.MovieDetailEntity
import com.eylulcan.moviefragment.domain.repository.MovieRepository

class MovieDetailUseCase (private val repository: MovieRepository) {

    suspend operator fun invoke(movieId: Int): MovieDetailEntity? {
        return repository.getMovieDetail(movieId)

    }
}