package com.eylulcan.moviefragment.data.model

import com.eylulcan.moviefragment.domain.entity.CastEntity
import com.eylulcan.moviefragment.domain.entity.CrewEntity
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

@Generated("jsonschema2pojo")
class ArtistMovieCredits {
    @SerializedName("cast")
    @Expose
    val castEntity: List<CastEntity>? = null

    @SerializedName("crew")
    @Expose
    val crewEntity: List<CrewEntity>? = null

    @SerializedName("id")
    @Expose
     val id: Int? = null
}