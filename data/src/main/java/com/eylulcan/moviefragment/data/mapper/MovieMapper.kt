package com.eylulcan.moviefragment.data.mapper

import com.eylulcan.moviefragment.data.model.GuestSession
import com.eylulcan.moviefragment.data.model.Movie
import com.eylulcan.moviefragment.domain.entity.GuestSessionEntity
import com.eylulcan.moviefragment.domain.entity.MovieEntity
import com.eylulcan.moviefragment.domain.entity.ResultMovieEntity

const val IF_INT_NULL = 0
const val IF_STR_NULL = ""
const val IF_BOOLEAN_NULL = false

class MovieMapper {

    fun convertToMovieEntity(movie: Movie): MovieEntity {
        val list : ArrayList<ResultMovieEntity> = arrayListOf()
        movie.results?.forEach {
            list.add(it)
        }
        return MovieEntity(
            page = movie.page ?: IF_INT_NULL,
            results = list,
            totalPages = movie.totalPages ?: IF_INT_NULL ,
            totalResults = movie.totalResults ?: IF_INT_NULL
        )
    }

    fun convertToGuestSessionEntity(guestSession: GuestSession): GuestSessionEntity {
        return GuestSessionEntity(
            success = guestSession.success ?: IF_BOOLEAN_NULL,
            sessionID = guestSession.sessionID ?: IF_STR_NULL
        )
    }

}