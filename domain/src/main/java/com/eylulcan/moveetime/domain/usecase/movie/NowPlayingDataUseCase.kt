package com.eylulcan.moveetime.domain.usecase.movie

import com.eylulcan.moveetime.domain.entity.MovieEntity
import com.eylulcan.moveetime.domain.repository.MovieRepository
import javax.inject.Inject

class NowPlayingDataUseCase @Inject constructor(private val repository: MovieRepository) {

    suspend operator fun invoke(): MovieEntity? {
        return repository.getNowPlayingData()
    }
}