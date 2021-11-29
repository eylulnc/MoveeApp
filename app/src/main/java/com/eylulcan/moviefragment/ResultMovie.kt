package com.eylulcan.moviefragment

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import javax.annotation.Generated

@Generated("jsonschema2pojo")
class ResultMovie: Serializable {

    @SerializedName("adult")
    @Expose
    private var adult: Boolean? = null

    @SerializedName("backdrop_path")
    @Expose
    private var backdropPath: String? = null

    @SerializedName("genre_ids")
    @Expose
    private var genreIds: List<Int?>? = null

    @SerializedName("id")
    @Expose
    private var id: Int? = null

    @SerializedName("original_language")
    @Expose
    private var originalLanguage: String? = null

    @SerializedName("original_title")
    @Expose
    private var originalTitle: String? = null

    @SerializedName("overview")
    @Expose
    var overview: String? = null

    @SerializedName("popularity")
    @Expose
    private var popularity: String? = null

    @SerializedName("poster_path")
    @Expose
    var posterPath: String? = null

    @SerializedName("release_date")
    @Expose
    private var releaseDate: String? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("video")
    @Expose
    private var video: Boolean? = null

    @SerializedName("vote_average")
    @Expose
    private var voteAverage: Double? = null

    @SerializedName("vote_count")
    @Expose
    private var voteCount: Int? = null

}