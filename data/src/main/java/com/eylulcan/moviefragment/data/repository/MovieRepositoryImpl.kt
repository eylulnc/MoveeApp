package com.eylulcan.moviefragment.data.repository

import com.eylulcan.moviefragment.data.datasource.MovieDataSource
import com.eylulcan.moviefragment.domain.entity.GuestSessionEntity
import com.eylulcan.moviefragment.domain.entity.MovieEntity
import com.eylulcan.moviefragment.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val dataSource: MovieDataSource) : MovieRepository {

    override suspend fun getPopularData(): MovieEntity? {
        return dataSource.getPopularData()
    }

    override suspend fun getTopRatedData(): MovieEntity? {
        return dataSource.getTopRatedData()
    }

    override suspend fun getNowPlayingData(): MovieEntity? {
        return dataSource.getNowPlayingData()
    }

    override suspend fun getUpcomingData(): MovieEntity? {
        return dataSource.getUpcomingData()
    }

    override suspend fun getGuestSessionId(): GuestSessionEntity? {
        return dataSource.getGuestSessionId()
    }

}