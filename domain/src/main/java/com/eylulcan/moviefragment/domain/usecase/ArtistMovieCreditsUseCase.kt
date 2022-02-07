package com.eylulcan.moviefragment.domain.usecase

import com.eylulcan.moviefragment.domain.entity.ArtistMovieCreditsEntity
import com.eylulcan.moviefragment.domain.repository.ArtistRepository

class ArtistMovieCreditsUseCase (private val artistRepository: ArtistRepository) {
    suspend fun invoke(id: Int): ArtistMovieCreditsEntity? {
        return artistRepository.getArtistMovieCredits(id)
    }
}