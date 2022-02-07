package com.eylulcan.moviefragment.domain.usecase

import com.eylulcan.moviefragment.domain.entity.MovieEntity
import com.eylulcan.moviefragment.domain.repository.MovieRepository
import javax.inject.Inject

class MoreMoviesUseCase @Inject constructor(private val repository: MovieRepository) {

    suspend operator fun invoke(movieId:Int): MovieEntity? {
        return repository.getMoreMovies(movieId)

    }
}