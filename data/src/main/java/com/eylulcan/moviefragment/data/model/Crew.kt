package com.eylulcan.moviefragment.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

@Generated("jsonschema2pojo")
class Crew {
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

    @SerializedName("popularity")
    @Expose
    val popularity: Double? = null

    @SerializedName("credit_id")
    @Expose
    val creditId: String? = null

    @SerializedName("department")
    @Expose
    val department: String? = null

    @SerializedName("job")
    @Expose
    val job: String? = null
}