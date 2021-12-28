package com.eylulcan.moviefragment.api

import com.eylulcan.moviefragment.model.*
import com.eylulcan.moviefragment.model.MovieCredits
import com.eylulcan.moviefragment.util.Utils
import retrofit2.Response
import retrofit2.http.*

interface MovieAPI {
    @GET("movie/popular")
    suspend fun getPopularData(@Query("api_key") apiKey: String = Utils.API_KEY): Response<Movie>

    @GET("movie/top_rated")
    suspend fun getTopRatedData(@Query("api_key") apiKey: String = Utils.API_KEY): Response<Movie>

    @GET("movie/now_playing")
    suspend fun getNowPlayingData(
        @Query("api_key") apiKey: String = Utils.API_KEY,
        @Query("page") pageNo: Int
    ): Response<Movie>

    @GET("genre/movie/list")
    suspend fun getGenresData(@Query("api_key") apiKey: String = Utils.API_KEY): Response<GenreList>

    @GET("person/popular")
    suspend fun getPopularPeople(
        @Query("api_key") apiKey: String = Utils.API_KEY,
        @Query("page") pageNo: Int
    ): Response<PopularPeopleList>

    @GET("person/{id}/movie_credits")
    suspend fun getArtistMovieCredits(
        @Path("id") personId: Int,
        @Query("api_key") apiKey: String = Utils.API_KEY
    ): Response<ArtistMovieCredits>

    @GET("person/{id}")
    suspend fun getArtistDetail(
        @Path("id") personId: Int,
        @Query("api_key") apiKey: String = Utils.API_KEY
    ): Response<ArtistDetail>

    @GET("person/{id}/images")
    suspend fun getArtistImages(
        @Path("id") personId: Int,
        @Query("api_key") apiKey: String = Utils.API_KEY
    ): Response<ArtistAlbum>

    @GET("movie/{id}/similar")
    suspend fun getMoreMovie(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String = Utils.API_KEY
    ): Response<Movie>

    @GET("movie/{id}/reviews")
    suspend fun getMovieReviews(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String = Utils.API_KEY,
        @Query("page") pageNo: Int
    ): Response<ReviewList>

    @GET("movie/{id}/credits")
    suspend fun getMovieCredits(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String = Utils.API_KEY
    ): Response<MovieCredits>

    @GET("movie/{id}/videos")
    suspend fun getMovieVideoClips(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String = Utils.API_KEY
    ): Response<VideoList>

    @GET("discover/movie")
    suspend fun getMovieByGenreId(
        @Query("api_key") apiKey: String = Utils.API_KEY,
        @Query("with_genres") genreId: Int,
        @Query("page") pageNo: Int
    ): Response<Movie>

    @GET("movie/{id}")
    suspend fun getMovieDetail(
        @Path("id") genreId: Int,
        @Query("api_key") apiKey: String = Utils.API_KEY
    ): Response<MovieDetail>

    @GET("search/multi")
    suspend fun getSearchResult(
        @Query("api_key") apiKey: String = Utils.API_KEY,
        @Query("query") query: String,
        @Query("page") pageNo: Int
    ): Response<SearchResultList>

    @GET("authentication/guest_session/new")
    suspend fun getGuestSessionId(@Query("api_key") apiKey: String = Utils.API_KEY)
            : Response<GuestSession>

    @POST("movie/{movie_id}/rating")
    suspend fun postMovieRating(
        @Path("movie_id") movieID: Int,
        @Query("api_key") apiKey: String = Utils.API_KEY,
        @Query("guest_session_id") sessionId: String,
        @Body postRatingBody: PostRatingBody
    ): Response<RatingPostResponse>
}