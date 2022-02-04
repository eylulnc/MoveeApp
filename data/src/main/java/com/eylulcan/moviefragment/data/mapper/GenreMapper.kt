package com.eylulcan.moviefragment.data.mapper

import com.eylulcan.moviefragment.data.model.GenreList
import com.eylulcan.moviefragment.domain.entity.GenreListEntity

class GenreMapper {
    fun convertToGenreEntity(genreList: GenreList): GenreListEntity {
        return GenreListEntity(
            genres = genreList.genres ?: arrayListOf()
        )
    }
}