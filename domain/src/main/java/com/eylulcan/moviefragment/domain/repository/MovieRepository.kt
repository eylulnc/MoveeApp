package com.eylulcan.moviefragment.domain.repository

import com.eylulcan.moviefragment.domain.entity.GuestSessionEntity
import com.eylulcan.moviefragment.domain.entity.MovieEntity
import com.eylulcan.moviefragment.domain.entity.PostRatingBodyEntity
import com.eylulcan.moviefragment.domain.entity.RatingPostResponseEntity

interface MovieRepository {
    suspend fun getPopularData(): MovieEntity?
    suspend fun getTopRatedData(): MovieEntity?
    suspend fun getNowPlayingData(): MovieEntity?
    suspend fun getUpcomingData(): MovieEntity?
    suspend fun getGuestSessionId() : GuestSessionEntity?
    suspend fun getGenreMovieList(genreId: Int, pageNo: Int): MovieEntity?
    suspend fun postRateMovie(movieId:Int, guestSessionId:String, postBody: PostRatingBodyEntity) : RatingPostResponseEntity?
}