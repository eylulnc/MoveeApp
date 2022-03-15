package com.eylulcan.moveetime.data.datasource.remote

import com.eylulcan.moveetime.domain.entity.GenreListEntity

interface GenreRemoteDataSource {
    suspend fun getGenres(): GenreListEntity?
}