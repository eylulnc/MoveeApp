package com.eylulcan.moviefragment.domain.usecase.artist

import com.eylulcan.moviefragment.domain.entity.ArtistDetailEntity
import com.eylulcan.moviefragment.domain.repository.ArtistRepository
import javax.inject.Inject

class ArtistDetailUseCase @Inject constructor(private val artistRepository: ArtistRepository) {
    suspend fun invoke(id: Int): ArtistDetailEntity? {
        return artistRepository.getArtistDetail(id)
    }
}