package com.eylulcan.moveetime.data.repository

import com.eylulcan.moveetime.data.datasource.remote.ArtistRemoteDataSource
import com.eylulcan.moveetime.domain.entity.ArtistAlbumEntity
import com.eylulcan.moveetime.domain.entity.ArtistDetailEntity
import com.eylulcan.moveetime.domain.entity.ArtistListEntity
import com.eylulcan.moveetime.domain.entity.ArtistMovieCreditsEntity
import com.eylulcan.moveetime.domain.repository.ArtistRepository
import javax.inject.Inject

class ArtistRepositoryImpl @Inject constructor(private val artistDataSource: ArtistRemoteDataSource) :
    ArtistRepository {
    override suspend fun getPopularPeople(pageNo: Int): ArtistListEntity? {
        return artistDataSource.getPopularPeople(pageNo)
    }

    override suspend fun getArtistDetail(id: Int): ArtistDetailEntity? {
        return artistDataSource.getArtistDetail(id)
    }

    override suspend fun getArtistAlbum(id: Int): ArtistAlbumEntity? {
        return artistDataSource.getArtistAlbum(id)
    }

    override suspend fun getArtistMovieCredits(id: Int): ArtistMovieCreditsEntity? {
        return artistDataSource.getArtistMovieCredits(id)
    }
}