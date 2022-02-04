package com.eylulcan.moviefragment.data.datasource

import com.eylulcan.moviefragment.data.mapper.GenreMapper
import com.eylulcan.moviefragment.data.service.MovieAPI
import com.eylulcan.moviefragment.domain.entity.GenreListEntity
import com.eylulcan.moviefragment.domain.repository.GenreRepository
import javax.inject.Inject

class GenreDataSource @Inject constructor(
    private val api: MovieAPI,
    private val genreMapper: GenreMapper
) :
    GenreRepository {
    override suspend fun getGenres(): GenreListEntity? {
        return api.getGenresData().body()?.let { genreMapper.convertToGenreEntity(it) }
    }
}