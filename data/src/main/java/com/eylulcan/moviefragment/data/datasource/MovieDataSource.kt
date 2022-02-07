package com.eylulcan.moviefragment.data.datasource

import com.eylulcan.moviefragment.data.mapper.MovieMapper
import com.eylulcan.moviefragment.data.model.PostRatingBody
import com.eylulcan.moviefragment.data.service.MovieAPI
import com.eylulcan.moviefragment.domain.entity.*
import com.eylulcan.moviefragment.domain.repository.MovieRepository
import javax.inject.Inject

class MovieDataSource @Inject constructor(
    private val api: MovieAPI,
    private val movieMapper: MovieMapper
) : MovieRepository {

    override suspend fun getPopularData(): MovieEntity? {
        return api.getPopularData().body()?.let { movieMapper.convertToMovieEntity(it) }
    }

    override suspend fun getTopRatedData(): MovieEntity? {
        return api.getTopRatedData().body()?.let { movieMapper.convertToMovieEntity(it) }
    }

    override suspend fun getNowPlayingData(): MovieEntity? {
        return api.getNowPlayingData().body()?.let { movieMapper.convertToMovieEntity(it) }
    }

    override suspend fun getUpcomingData(): MovieEntity? {
        return api.getUpcomingData().body()?.let { movieMapper.convertToMovieEntity(it) }
    }

    override suspend fun getGuestSessionId(): GuestSessionEntity? {
        return api.getGuestSessionId().body()?.let { movieMapper.convertToGuestSessionEntity(it) }
    }

    override suspend fun getGenreMovieList(genreId: Int, pageNo: Int): MovieEntity? {
        return api.getMovieByGenreId(genreId = genreId, pageNo = pageNo).body()
            ?.let { movieMapper.convertToMovieEntity(it) }
    }

    override suspend fun postRateMovie(
        movieId: Int,
        guestSessionId: String,
        postBody: PostRatingBodyEntity
    ): RatingPostResponseEntity? {
        return api.postMovieRating(
            movieID = movieId,
            sessionId = guestSessionId,
            postRatingBodyEntity = postBody
        ).body()?.let{ movieMapper.convertToRatingPostResponseEntity(it)}

    }

    override suspend fun getMovieCredits(movieId: Int): MovieCreditsEntity? {
       return api.getMovieCredits(movieId).body()?.let { movieMapper.convertToMovieCreditsEntity(it) }
    }

    override suspend fun getMovieReviews(movieId: Int, pageNo: Int): ReviewListEntity? {
        return api.getMovieReviews(movieId = movieId, pageNo = pageNo).body()?.let {
            movieMapper.convertToReviewListEntity(it)
        }
    }

    override suspend fun getMoreMovies(movieId: Int): MovieEntity? {
        return api.getMoreMovie(movieId).body()?.let { movieMapper.convertToMovieEntity(it) }
    }

    override suspend fun getMovieVideo(movieId: Int): VideoListEntity? {
        return api.getMovieVideoClips(movieId).body()?.let { movieMapper.convertToVideoListEntity(it) }
    }

    override suspend fun getMovieDetail(movieId: Int): MovieDetailEntity? {
        return api.getMovieDetail(movieId).body()?.let { movieMapper.convertToMovieDetailEntity(it) }
    }


}