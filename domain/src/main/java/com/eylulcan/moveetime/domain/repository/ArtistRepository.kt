package com.eylulcan.moveetime.domain.repository

import com.eylulcan.moveetime.domain.entity.ArtistAlbumEntity
import com.eylulcan.moveetime.domain.entity.ArtistDetailEntity
import com.eylulcan.moveetime.domain.entity.ArtistListEntity
import com.eylulcan.moveetime.domain.entity.ArtistMovieCreditsEntity

interface ArtistRepository {
    suspend fun getPopularPeople(pageNo: Int): ArtistListEntity?
    suspend fun getArtistDetail(id: Int): ArtistDetailEntity?
    suspend fun getArtistAlbum(id: Int): ArtistAlbumEntity?
    suspend fun getArtistMovieCredits(id: Int): ArtistMovieCreditsEntity?
}