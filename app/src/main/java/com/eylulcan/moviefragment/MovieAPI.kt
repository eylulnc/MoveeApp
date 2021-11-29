package com.eylulcan.moviefragment

import retrofit2.Response
import retrofit2.http.GET

interface MovieAPI {
    @GET("movie/popular?api_key=a2d3d4e6888e49e2bcbb7ffe79963274&language=en-US")
    suspend fun getPopularData(): Response<Movie>

    @GET("movie/top_rated?api_key=a2d3d4e6888e49e2bcbb7ffe79963274&language=en-US")
    suspend fun getTopRatedData(): Response<Movie>
}