package com.eylulcan.moviefragment.data.model

import com.eylulcan.moviefragment.domain.entity.MovieCast
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