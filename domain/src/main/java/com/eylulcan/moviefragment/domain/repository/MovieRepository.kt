package com.eylulcan.moviefragment.domain.repository

import com.eylulcan.moviefragment.domain.entity.*

interface MovieRepository {
    suspend fun getPopularData(): MovieEntity?
    suspend fun getTopRatedData(): MovieEntity?
    suspend fun getNowPlayingData(): MovieEntity?
    suspend fun getUpcomingData(): MovieEntity?
    suspend fun getGuestSessionId() : GuestSessionEntity?
    suspend fun getGenreMovieList(genreId: Int, pageNo: Int): MovieEntity?
    suspend fun postRateMovie(movieId:Int, guestSessionId:String, postBody: PostRatingBodyEntity): RatingPostResponseEntity?
    suspend fun getMovieCredits(movieId: Int): MovieCreditsEntity?
    suspend fun getMovieReviews(movieId: Int, pageNo: Int): ReviewListEntity?
    suspend fun getMoreMovies(movieId: Int): MovieEntity?
    suspend fun getMovieVideo(movieId: Int): VideoListEntity?
    suspend fun getMovieDetail(movieId: Int):MovieDetailEntity?
}