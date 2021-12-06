package com.eylulcan.moviefragment.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

@Generated("jsonschema2pojo")
class ArtistMovieCredits {
    @SerializedName("cast")
    @Expose
    private val cast: List<Cast>? = null

    @SerializedName("crew")
    @Expose
    private val crew: List<Crew>? = null

    @SerializedName("id")
    @Expose
    private val id: Int? = null
}