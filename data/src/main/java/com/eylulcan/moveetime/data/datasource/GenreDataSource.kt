package com.eylulcan.moveetime.data.datasource

import com.eylulcan.moveetime.data.datasource.remote.GenreRemoteDataSource
import com.eylulcan.moveetime.data.mapper.GenreMapper
import com.eylulcan.moveetime.data.service.MovieAPI
import com.eylulcan.moveetime.domain.entity.GenreListEntity
import javax.inject.Inject

class GenreDataSource @Inject constructor(
    private val api: MovieAPI,
    private val genreMapper: GenreMapper
) : GenreRemoteDataSource {
    override suspend fun getGenres(): GenreListEntity? {
        return api.getGenresData().body()?.let { genreMapper.convertToGenreEntity(it) }
    }
}