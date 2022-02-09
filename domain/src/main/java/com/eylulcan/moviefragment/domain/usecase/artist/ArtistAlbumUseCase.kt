package com.eylulcan.moviefragment.domain.usecase.artist

import com.eylulcan.moviefragment.domain.entity.ArtistAlbumEntity
import com.eylulcan.moviefragment.domain.repository.ArtistRepository
import javax.inject.Inject

class ArtistAlbumUseCase @Inject constructor(private val artistRepository: ArtistRepository) {
    suspend fun invoke(id: Int): ArtistAlbumEntity? {
        return artistRepository.getArtistAlbum(id)
    }
}