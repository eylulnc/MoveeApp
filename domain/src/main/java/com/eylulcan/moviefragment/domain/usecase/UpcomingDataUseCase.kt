package com.eylulcan.moviefragment.domain.usecase

import com.eylulcan.moviefragment.domain.entity.MovieEntity
import com.eylulcan.moviefragment.domain.repository.MovieRepository

class UpcomingDataUseCase (private val repository: MovieRepository) {

    suspend operator fun invoke(): MovieEntity? {
        return repository.getUpcomingData()

    }
}