package com.eylulcan.moviefragment.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResultTvShow {
    @SerializedName("backdrop_path")
    @Expose
    private val backdropPath: String? = null

    @SerializedName("first_air_date")
    @Expose
    private val firstAirDate: String? = null

    @SerializedName("genre_ids")
    @Expose
    private val genreIds: List<Int>? = null

    @SerializedName("id")
    @Expose
    private val id: Int? = null

    @SerializedName("name")
    @Expose
    val name: String? = null

    @SerializedName("origin_country")
    @Expose
    private val originCountry: List<String>? = null

    @SerializedName("original_language")
    @Expose
    private val originalLanguage: String? = null

    @SerializedName("original_name")
    @Expose
    private val originalName: String? = null

    @SerializedName("overview")
    @Expose
    private val overview: String? = null

    @SerializedName("popularity")
    @Expose
    private val popularity: Double? = null

    @SerializedName("poster_path")
    @Expose
    val posterPath: String? = null

    @SerializedName("vote_average")
    @Expose
    private val voteAverage: Double? = null

    @SerializedName("vote_count")
    @Expose
    private val voteCount: Int? = null
}