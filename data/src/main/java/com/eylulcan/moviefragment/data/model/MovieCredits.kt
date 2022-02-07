package com.eylulcan.moviefragment.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

@Generated("jsonschema2pojo")
class MovieCredits {

    @SerializedName("id")
    @Expose
    val id: Int? = null

    @SerializedName("cast")
    @Expose
    val cast: List<MovieCast>? = null
}