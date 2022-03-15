package com.eylulcan.moveetime.domain.usecase.artist

import com.eylulcan.moveetime.domain.entity.ArtistMovieCreditsEntity
import com.eylulcan.moveetime.domain.repository.ArtistRepository
import javax.inject.Inject

class ArtistMovieCreditsUseCase @Inject constructor(private val artistRepository: ArtistRepository) {
    suspend fun invoke(id: Int): ArtistMovieCreditsEntity? {
        return artistRepository.getArtistMovieCredits(id)
    }
}