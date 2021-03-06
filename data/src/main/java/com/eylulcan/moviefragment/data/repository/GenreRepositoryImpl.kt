package com.eylulcan.moviefragment.data.repository

import com.eylulcan.moviefragment.data.datasource.remote.GenreRemoteDataSource
import com.eylulcan.moviefragment.domain.entity.GenreListEntity
import com.eylulcan.moviefragment.domain.repository.GenreRepository
import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(private val genreDataSource: GenreRemoteDataSource) :
    GenreRepository {

    override suspend fun getGenres(): GenreListEntity? {
        return genreDataSource.getGenres()
    }
}