package com.eylulcan.moviefragment.data.mapper

import com.eylulcan.moviefragment.data.Utils.IF_BOOLEAN_NULL
import com.eylulcan.moviefragment.data.Utils.IF_INT_NULL
import com.eylulcan.moviefragment.data.Utils.IF_STR_NULL
import com.eylulcan.moviefragment.data.model.GuestSession
import com.eylulcan.moviefragment.data.model.Movie
import com.eylulcan.moviefragment.data.model.RatingPostResponse
import com.eylulcan.moviefragment.domain.entity.*


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

    fun convertToRatingPostResponseEntity(ratingPostResponse: RatingPostResponse): RatingPostResponseEntity{
        return RatingPostResponseEntity(
            success = ratingPostResponse.success ?: IF_BOOLEAN_NULL,
            statusCode = ratingPostResponse.statusCode ?: IF_INT_NULL,
            statusMessage = ratingPostResponse.statusMessage ?: IF_STR_NULL
        )

    }

}