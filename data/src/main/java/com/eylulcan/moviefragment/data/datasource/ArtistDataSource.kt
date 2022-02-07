package com.eylulcan.moviefragment.data.datasource

import com.eylulcan.moviefragment.data.mapper.ArtistMapper
import com.eylulcan.moviefragment.data.service.MovieAPI
import com.eylulcan.moviefragment.domain.entity.ArtistListEntity
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
}