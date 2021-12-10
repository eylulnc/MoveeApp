package com.eylulcan.moviefragment.model

import javax.annotation.Generated
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Generated("jsonschema2pojo")
class VideoList {
    @SerializedName("id")
    @Expose
    private val id: Int? = null

    @SerializedName("results")
    @Expose
    val results: List<VideoResult>? = null
}