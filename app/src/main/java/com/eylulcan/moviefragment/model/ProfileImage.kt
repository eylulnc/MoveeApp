package com.eylulcan.moviefragment.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated


@Generated("jsonschema2pojo")
class ProfileImage {
    @SerializedName("aspect_ratio")
    @Expose
    private val aspectRatio: Double? = null

    @SerializedName("height")
    @Expose
    private val height: Int? = null

    @SerializedName("iso_639_1")
    @Expose
    private val iso6391: Any? = null

    @SerializedName("file_path")
    @Expose
    val filePath: String? = null

    @SerializedName("vote_average")
    @Expose
    private val voteAverage: Double? = null

    @SerializedName("vote_count")
    @Expose
    private val voteCount: Int? = null

    @SerializedName("width")
    @Expose
    private val width: Int? = null
}