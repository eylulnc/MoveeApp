package com.eylulcan.moveetime.data.repository

import com.eylulcan.moveetime.data.datasource.remote.MovieRemoteDataSource
import com.eylulcan.moveetime.domain.entity.*
import com.eylulcan.moveetime.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val dataSource: MovieRemoteDataSource) :
    MovieRepository {

    override suspend fun getPopularData(): MovieEntity? {
        return dataSource.getPopularData()
    }

    override suspend fun getTopRatedData(): MovieEntity? {
        return dataSource.getTopRatedData()
    }

    override suspend fun getNowPlayingData(): MovieEntity? {
        return dataSource.getNowPlayingData()
    }

    override suspend fun getUpcomingData(): MovieEntity? {
        return dataSource.getUpcomingData()
    }

    override suspend fun getGuestSessionId(): GuestSessionEntity? {
        return dataSource.getGuestSessionId()
    }

    override suspend fun getGenreMovieList(genreId: Int, pageNo: Int): MovieEntity? {
        return dataSource.getGenreMovieList(genreId, pageNo)
    }

    override suspend fun postRateMovie(
        movieId: Int,
        guestSessionId: String,
        postBody: PostRatingBodyEntity
    ): RatingPostResponseEntity? {
        return dataSource.postRateMovie(movieId, guestSessionId, postBody)
    }

    override suspend fun getMovieCredits(movieId: Int): MovieCreditsEntity? {
        return dataSource.getMovieCredits(movieId)
    }

    override suspend fun getMovieReviews(movieId: Int, pageNo: Int): ReviewListEntity? {
        return dataSource.getMovieReviews(movieId, pageNo)
    }

    override suspend fun getMoreMovies(movieId: Int): MovieEntity? {
        return dataSource.getMoreMovies(movieId)
    }

    override suspend fun getMovieVideo(movieId: Int): VideoListEntity? {
        return dataSource.getMovieVideo(movieId)
    }

    override suspend fun getMovieDetail(movieId: Int): MovieDetailEntity? {
        return dataSource.getMovieDetail(movieId)
    }
}