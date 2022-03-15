package com.eylulcan.moveetime.domain.usecase.artist

import com.eylulcan.moveetime.domain.entity.ArtistAlbumEntity
import com.eylulcan.moveetime.domain.repository.ArtistRepository
import javax.inject.Inject

class ArtistAlbumUseCase @Inject constructor(private val artistRepository: ArtistRepository) {
    suspend fun invoke(id: Int): ArtistAlbumEntity? {
        return artistRepository.getArtistAlbum(id)
    }
}