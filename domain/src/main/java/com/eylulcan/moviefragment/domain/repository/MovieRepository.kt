package com.eylulcan.moviefragment.domain.repository

import com.eylulcan.moviefragment.domain.entity.GuestSessionEntity
import com.eylulcan.moviefragment.domain.entity.MovieEntity

interface MovieRepository {
    suspend fun getPopularData(): MovieEntity?
    suspend fun getTopRatedData(): MovieEntity?
    suspend fun getNowPlayingData(): MovieEntity?
    suspend fun getUpcomingData(): MovieEntity?
    suspend fun getGuestSessionId() : GuestSessionEntity?
}