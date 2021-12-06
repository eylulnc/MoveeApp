package com.eylulcan.moviefragment.model

import javax.annotation.Generated
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

@Generated("jsonschema2pojo")
class KnownFor {

    @SerializedName("adult")
    @Expose
    private val adult: Boolean? = null

    @SerializedName("backdrop_path")
    @Expose
    private val backdropPath: String? = null

    @SerializedName("genre_ids")
    @Expose
    private val genreIds: List<Int>? = null

    @SerializedName("id")
    @Expose
    private val id: Int? = null

    @SerializedName("media_type")
    @Expose
    private val mediaType: String? = null

    @SerializedName("original_language")
    @Expose
    private val originalLanguage: String? = null

    @SerializedName("original_title")
    @Expose
    private val originalTitle: String? = null

    @SerializedName("overview")
    @Expose
    private val overview: String? = null

    @SerializedName("poster_path")
    @Expose
    private val posterPath: String? = null

    @SerializedName("release_date")
    @Expose
    private val releaseDate: String? = null

    @SerializedName("title")
    @Expose
    private val title: String? = null

    @SerializedName("video")
    @Expose
    private val video: Boolean? = null

    @SerializedName("vote_average")
    @Expose
    private val voteAverage: Double? = null

    @SerializedName("vote_count")
    @Expose
    private val voteCount: Int? = null

    @SerializedName("first_air_date")
    @Expose
    private val firstAirDate: String? = null

    @SerializedName("name")
    @Expose
    private val name: String? = null

    @SerializedName("origin_country")
    @Expose
    private val originCountry: List<String>? = null

    @SerializedName("original_name")
    @Expose
    private val originalName: String? = null
}