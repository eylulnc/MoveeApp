package com.eylulcan.moviefragment.data.model

import com.eylulcan.moviefragment.domain.entity.VideoResultEntity
import javax.annotation.Generated
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Generated("jsonschema2pojo")
class VideoList {
    @SerializedName("id")
    @Expose
    val id: Int? = null

    @SerializedName("results")
    @Expose
    val results: List<VideoResultEntity>? = null
}