package com.eylulcan.moviefragment.data.datasource

import com.eylulcan.moviefragment.data.mapper.MovieMapper
import com.eylulcan.moviefragment.data.service.MovieAPI
import com.eylulcan.moviefragment.domain.entity.GuestSessionEntity
import com.eylulcan.moviefragment.domain.entity.MovieEntity
import com.eylulcan.moviefragment.domain.repository.MovieRepository
import javax.inject.Inject

class MovieDataSource @Inject constructor(
    private val api: MovieAPI,
    private val movieMapper: MovieMapper
) : MovieRepository {

    override suspend fun getPopularData(): MovieEntity? {
        return api.getPopularData().body()?.let { movieMapper.convertToMovieEntity(it) }
    }

    override suspend fun getTopRatedData(): MovieEntity? {
        return api.getTopRatedData().body()?.let { movieMapper.convertToMovieEntity(it) }
    }

    override suspend fun getNowPlayingData(): MovieEntity? {
        return api.getNowPlayingData().body()?.let { movieMapper.convertToMovieEntity(it) }
    }

    override suspend fun getUpcomingData(): MovieEntity? {
        return api.getUpcomingData().body()?.let { movieMapper.convertToMovieEntity(it) }
    }

    override suspend fun getGuestSessionId(): GuestSessionEntity? {
        return api.getGuestSessionId().body()?.let { movieMapper.convertToGuestSessionEntity(it) }
    }
}