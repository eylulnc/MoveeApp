package com.eylulcan.moviefragment.data.datasource.remote

import com.eylulcan.moviefragment.domain.entity.GenreListEntity

interface GenreRemoteDataSource {
    suspend fun getGenres(): GenreListEntity?
}