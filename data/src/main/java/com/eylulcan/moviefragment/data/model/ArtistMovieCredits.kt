package com.eylulcan.moviefragment.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

@Generated("jsonschema2pojo")
class ArtistMovieCredits {
    @SerializedName("cast")
    @Expose
    val cast: List<Cast>? = null

    @SerializedName("crew")
    @Expose
    val crew: List<Crew>? = null

    @SerializedName("id")
    @Expose
    val id: Int? = null
}