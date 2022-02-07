package com.eylulcan.moviefragment.domain.usecase

import com.eylulcan.moviefragment.domain.entity.ArtistDetailEntity
import com.eylulcan.moviefragment.domain.repository.ArtistRepository

class ArtistDetailUseCase(private val artistRepository: ArtistRepository) {
    suspend fun invoke(id: Int): ArtistDetailEntity? {
        return artistRepository.getArtistDetail(id)
    }
}