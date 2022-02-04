package com.eylulcan.moviefragment.domain.repository

import com.eylulcan.moviefragment.domain.entity.GenreListEntity

interface GenreRepository {
    suspend fun getGenres() : GenreListEntity?
}