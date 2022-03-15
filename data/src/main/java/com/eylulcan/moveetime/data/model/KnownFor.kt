package com.eylulcan.moveetime.data.model

import javax.annotation.Generated
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

@Generated("jsonschema2pojo")
class KnownFor {

    @SerializedName("adult")
    @Expose
    val adult: Boolean? = null

    @SerializedName("backdrop_path")
    @Expose
    val backdropPath: String? = null

    @SerializedName("genre_ids")
    @Expose
    val genreIds: List<Int>? = null

    @SerializedName("id")
    @Expose
    val id: Int? = null

    @SerializedName("media_type")
    @Expose
    val mediaType: String? = null

    @SerializedName("original_language")
    @Expose
    val originalLanguage: String? = null

    @SerializedName("original_title")
    @Expose
    val originalTitle: String? = null

    @SerializedName("overview")
    @Expose
    val overview: String? = null

    @SerializedName("poster_path")
    @Expose
    val posterPath: String? = null

    @SerializedName("release_date")
    @Expose
    val releaseDate: String? = null

    @SerializedName("title")
    @Expose
    val title: String? = null

    @SerializedName("video")
    @Expose
    val video: Boolean? = null

    @SerializedName("vote_average")
    @Expose
    val voteAverage: Double? = null

    @SerializedName("vote_count")
    @Expose
    val voteCount: Int? = null

    @SerializedName("first_air_date")
    @Expose
    val firstAirDate: String? = null

    @SerializedName("name")
    @Expose
    val name: String? = null

    @SerializedName("origin_country")
    @Expose
    val originCountry: List<String>? = null

    @SerializedName("original_name")
    @Expose
    val originalName: String? = null
}