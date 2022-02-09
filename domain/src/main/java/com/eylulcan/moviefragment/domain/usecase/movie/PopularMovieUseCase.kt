package com.eylulcan.moviefragment.domain.usecase.movie

import com.eylulcan.moviefragment.domain.entity.MovieEntity
import com.eylulcan.moviefragment.domain.repository.MovieRepository
import javax.inject.Inject

class PopularMovieUseCase @Inject constructor(private val repository: MovieRepository) {

    suspend operator fun invoke(): MovieEntity? {
        return repository.getPopularData()

    }
}