package com.eylulcan.moviefragment.data.mapper

import com.eylulcan.moviefragment.data.Utils
import com.eylulcan.moviefragment.data.model.GuestSession
import com.eylulcan.moviefragment.data.model.Movie
import com.eylulcan.moviefragment.domain.entity.GuestSessionEntity
import com.eylulcan.moviefragment.domain.entity.MovieEntity
import com.eylulcan.moviefragment.domain.entity.ResultMovieEntity

class MovieMapper {

    fun convertToMovieEntity(movie: Movie): MovieEntity {
        val list : ArrayList<ResultMovieEntity> = arrayListOf()
        movie.results?.forEach {
            list.add(it)
        }
        return MovieEntity(
            page = movie.page ?: Utils.IF_INT_NULL,
            results = list,
            totalPages = movie.totalPages ?: Utils.IF_INT_NULL ,
            totalResults = movie.totalResults ?: Utils.IF_INT_NULL
        )
    }

    fun convertToGuestSessionEntity(guestSession: GuestSession): GuestSessionEntity {
        return GuestSessionEntity(
            success = guestSession.success ?: Utils.IF_BOOLEAN_NULL,
            sessionID = guestSession.sessionID ?: Utils.IF_STR_NULL
        )
    }

}