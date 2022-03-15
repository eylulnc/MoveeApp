package com.eylulcan.moveetime.data.repository

import com.eylulcan.moveetime.data.datasource.remote.GenreRemoteDataSource
import com.eylulcan.moveetime.domain.entity.GenreListEntity
import com.eylulcan.moveetime.domain.repository.GenreRepository
import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(private val genreDataSource: GenreRemoteDataSource) :
    GenreRepository {

    override suspend fun getGenres(): GenreListEntity? {
        return genreDataSource.getGenres()
    }
}