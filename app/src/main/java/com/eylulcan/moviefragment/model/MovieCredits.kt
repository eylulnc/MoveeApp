package com.eylulcan.moviefragment.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

@Generated("jsonschema2pojo")
class MovieCredits {

    @SerializedName("id")
    @Expose
    private val id: Int? = null

    @SerializedName("cast")
    @Expose
    val cast: List<MovieCast>? = null
}