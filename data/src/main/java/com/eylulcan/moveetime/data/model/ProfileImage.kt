package com.eylulcan.moveetime.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

@Generated("jsonschema2pojo")
class ProfileImage {
    @SerializedName("aspect_ratio")
    @Expose
    val aspectRatio: Double? = null

    @SerializedName("height")
    @Expose
    val height: Int? = null

    @SerializedName("iso_639_1")
    @Expose
    val iso6391: Int? = null

    @SerializedName("file_path")
    @Expose
    val filePath: String? = null

    @SerializedName("vote_average")
    @Expose
    val voteAverage: Double? = null

    @SerializedName("vote_count")
    @Expose
    val voteCount: Int? = null

    @SerializedName("width")
    @Expose
    val width: Int? = null
}