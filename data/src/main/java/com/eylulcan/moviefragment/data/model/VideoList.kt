package com.eylulcan.moviefragment.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

@Generated("jsonschema2pojo")
class VideoList {
    @SerializedName("id")
    @Expose
    val id: Int? = null

    @SerializedName("results")
    @Expose
    val results: List<VideoResult>? = null
}