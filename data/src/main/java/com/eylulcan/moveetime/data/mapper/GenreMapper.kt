package com.eylulcan.moveetime.data.mapper

import com.eylulcan.moveetime.data.Utils
import com.eylulcan.moveetime.data.model.Genre
import com.eylulcan.moveetime.data.model.GenreList
import com.eylulcan.moveetime.domain.entity.GenreEntity
import com.eylulcan.moveetime.domain.entity.GenreListEntity
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