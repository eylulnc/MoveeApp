package com.eylulcan.moviefragment.domain.usecase

import com.eylulcan.moviefragment.domain.entity.ArtistAlbumEntity
import com.eylulcan.moviefragment.domain.repository.ArtistRepository

class ArtistAlbumUseCase (private val artistRepository: ArtistRepository) {
    suspend fun invoke(id: Int): ArtistAlbumEntity? {
        return artistRepository.getArtistAlbum(id)
    }
}