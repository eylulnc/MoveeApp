package com.eylulcan.moviefragment.domain.usecase

import com.eylulcan.moviefragment.domain.entity.ArtistMovieCreditsEntity
import com.eylulcan.moviefragment.domain.repository.ArtistRepository
import javax.inject.Inject

class ArtistMovieCreditsUseCase @Inject constructor(private val artistRepository: ArtistRepository) {
    suspend fun invoke(id: Int): ArtistMovieCreditsEntity? {
        return artistRepository.getArtistMovieCredits(id)
    }
}