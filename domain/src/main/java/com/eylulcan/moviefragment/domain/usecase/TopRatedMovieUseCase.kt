package com.eylulcan.moviefragment.domain.usecase

import com.eylulcan.moviefragment.domain.entity.MovieEntity
import com.eylulcan.moviefragment.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class TopRatedMovieUseCase (private val repository: MovieRepository) {

    suspend operator fun invoke(): MovieEntity? {
        return repository.getTopRatedData()
    }
}