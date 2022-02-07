package com.eylulcan.moviefragment.data.mapper

import com.eylulcan.moviefragment.data.Utils
import com.eylulcan.moviefragment.data.model.Genre
import com.eylulcan.moviefragment.data.model.GenreList
import com.eylulcan.moviefragment.domain.entity.GenreEntity
import com.eylulcan.moviefragment.domain.entity.GenreListEntity
import javax.inject.Inject

class GenreMapper @Inject constructor() {
    fun convertToGenreEntity(genreList: GenreList): GenreListEntity {
        val genres = arrayListOf<GenreEntity>()
        genreList.genres?.forEach {
            genres.add(convertToGenreEntity(it))
        }
        return GenreListEntity(
            genres = genres
        )
    }

    private fun convertToGenreEntity(genre: Genre): GenreEntity {
        return GenreEntity(
            id = genre.id ?: Utils.IF_INT_NULL,
            name = genre.name ?: Utils.IF_STR_NULL
        )
    }
}