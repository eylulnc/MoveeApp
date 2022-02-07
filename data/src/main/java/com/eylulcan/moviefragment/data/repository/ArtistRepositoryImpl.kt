package com.eylulcan.moviefragment.data.repository

import com.eylulcan.moviefragment.data.datasource.ArtistDataSource
import com.eylulcan.moviefragment.domain.entity.ArtistListEntity
import com.eylulcan.moviefragment.domain.repository.ArtistRepository
import javax.inject.Inject

class ArtistRepositoryImpl @Inject constructor(private val artistDataSource: ArtistDataSource) : ArtistRepository {
    override suspend fun getPopularPeople(pageNo: Int): ArtistListEntity? {
        return artistDataSource.getPopularPeople(pageNo)
    }
}