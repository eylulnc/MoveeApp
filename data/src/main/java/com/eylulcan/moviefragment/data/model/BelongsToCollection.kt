package com.eylulcan.moviefragment.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

@Generated("jsonschema2pojo")
class BelongsToCollection {

    @SerializedName("id")
    @Expose
    val id: Int? = null

    @SerializedName("name")
    @Expose
    val name: String? = null

    @SerializedName("poster_path")
    @Expose
    val posterPath: String? = null

    @SerializedName("backdrop_path")
    @Expose
    val backdropPath: String? = null
}