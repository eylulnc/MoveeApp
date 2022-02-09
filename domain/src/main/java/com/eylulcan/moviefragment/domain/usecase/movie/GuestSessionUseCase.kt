package com.eylulcan.moviefragment.domain.usecase.movie

import com.eylulcan.moviefragment.domain.entity.GuestSessionEntity
import com.eylulcan.moviefragment.domain.repository.MovieRepository
import javax.inject.Inject

class GuestSessionUseCase @Inject constructor(private val repository: MovieRepository) {
    suspend operator fun invoke(): GuestSessionEntity? {
        return repository.getGuestSessionId()
    }
}