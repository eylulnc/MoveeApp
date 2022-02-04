package com.eylulcan.moviefragment.domain.usecase

import com.eylulcan.moviefragment.domain.entity.MovieEntity
import com.eylulcan.moviefragment.domain.repository.MovieRepository

class NowPlayingDataUseCase (private val repository: MovieRepository) {

    suspend operator fun invoke(): MovieEntity? {
        return repository.getNowPlayingData()

    }
}