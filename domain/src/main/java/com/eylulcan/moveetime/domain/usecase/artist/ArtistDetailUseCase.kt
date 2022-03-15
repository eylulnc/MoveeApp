package com.eylulcan.moveetime.domain.usecase.artist

import com.eylulcan.moveetime.domain.entity.ArtistDetailEntity
import com.eylulcan.moveetime.domain.repository.ArtistRepository
import javax.inject.Inject

class ArtistDetailUseCase @Inject constructor(private val artistRepository: ArtistRepository) {
    suspend fun invoke(id: Int): ArtistDetailEntity? {
        return artistRepository.getArtistDetail(id)
    }
}