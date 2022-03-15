package com.eylulcan.moveetime.domain.repository

import com.eylulcan.moveetime.domain.entity.GenreListEntity

interface GenreRepository {
    suspend fun getGenres(): GenreListEntity?
}