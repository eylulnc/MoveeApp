package com.eylulcan.moviefragment.api

import com.eylulcan.moviefragment.model.*
import com.eylulcan.moviefragment.model.MovieCredits
import com.eylulcan.moviefragment.util.Utils
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPI {
    @GET("movie/popular?api_key=a2d3d4e6888e49e2bcbb7ffe79963274&language=en-US")
    suspend fun getPopularData(): Response<Movie>

    @GET("movie/top_rated?api_key=a2d3d4e6888e49e2bcbb7ffe79963274&language=en-US")
    suspend fun getTopRatedData(): Response<Movie>

    @GET("genre/movie/list?api_key=a2d3d4e6888e49e2bcbb7ffe79963274&language=en-US")
    suspend fun getGenresData(): Response<GenreList>

    @GET("person/popular?api_key=a2d3d4e6888e49e2bcbb7ffe79963274&language=en-US")
    suspend fun getPopularPeople(): Response<PopularPeopleList>

    @GET("person/{id}/movie_credits?api_key=a2d3d4e6888e49e2bcbb7ffe79963274&language=en-US")
    suspend fun getArtistMovieCredits(@Path("id")id: Int): Response<ArtistMovieCredits>

    @GET("person/{id}?api_key=a2d3d4e6888e49e2bcbb7ffe79963274&language=en-US")
    suspend fun getArtistDetail(@Path("id")id: Int): Response<ArtistDetail>

    @GET("person/{id}/images?api_key=a2d3d4e6888e49e2bcbb7ffe79963274&language=en-US")
    suspend fun getArtistImages(@Path("id")id: Int): Response<ArtistAlbum>

    @GET("movie/{id}/similar?api_key=a2d3d4e6888e49e2bcbb7ffe79963274&language=en-US")
    suspend fun getMoreMovie(@Path("id")id: Int): Response<Movie>

    @GET("movie/{id}/reviews?api_key=a2d3d4e6888e49e2bcbb7ffe79963274&language=en-US")
    suspend fun getMovieReviews(@Path("id")id: Int): Response<ReviewList>

    @GET("movie/{id}/credits?api_key=a2d3d4e6888e49e2bcbb7ffe79963274&language=en-US")
    suspend fun getMovieCredits(@Path("id")id: Int): Response<MovieCredits>

    @GET("movie/{id}/videos?api_key=a2d3d4e6888e49e2bcbb7ffe79963274&language=en-US")
    suspend fun getMovieVideoClips(@Path("id")id: Int): Response<VideoList>

    @GET("movie/{id}?api_key=a2d3d4e6888e49e2bcbb7ffe79963274&language=en-US")
    suspend fun getMovieDetail(@Path("id")id: Int): Response<MovieDetail>

   /* @GET("discover/movie?api_key=a2d3d4e6888e49e2bcbb7ffe79963274&language=en-US&with_genres={genreId}")
    suspend fun getMovieByGenreId(@Path("genreId")genreId: Int): Response<Movie>*/

    @Headers("api_key: ${Utils.API_KEY}")
    @GET("discover/movie")
    suspend fun getMovieByGenreId(@Query("with_genres") genreId: Int): Response<Movie>
}