package com.eylulcan.moviefragment.data.datasource

import com.eylulcan.moviefragment.data.mapper.ArtistMapper
import com.eylulcan.moviefragment.data.service.MovieAPI
import com.eylulcan.moviefragment.domain.entity.ArtistAlbumEntity
import com.eylulcan.moviefragment.domain.entity.ArtistDetailEntity
import com.eylulcan.moviefragment.domain.entity.ArtistListEntity
import com.eylulcan.moviefragment.domain.entity.ArtistMovieCreditsEntity
import com.eylulcan.moviefragment.domain.repository.ArtistRepository
import javax.inject.Inject

class ArtistDataSource @Inject constructor(
    private val api: MovieAPI,
    private val artistMapper: ArtistMapper
) :
    ArtistRepository {

    override suspend fun getPopularPeople(pageNo: Int): ArtistListEntity? {
        return api.getPopularPeople(pageNo = pageNo).body()?.let { artistMapper.convertToArtistListEntity(it) }
    }

    override suspend fun getArtistDetail(id: Int): ArtistDetailEntity? {
        return api.getArtistDetail(id).body()?.let { artistMapper.convertToArtistDetailEntity(it) }
    }

    override suspend fun getArtistAlbum(id: Int): ArtistAlbumEntity? {
        return api.getArtistImages(id).body()?.let { artistMapper.convertToArtistAlbumEntity(it) }
    }

    override suspend fun getArtistMovieCredits(id: Int): ArtistMovieCreditsEntity? {
        return api.getArtistMovieCredits(id).body()?.let { artistMapper.convertToMovieCreditsEntity(it) }
    }
}