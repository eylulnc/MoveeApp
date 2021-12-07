package com.eylulcan.moviefragment.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated


@Generated("jsonschema2pojo")
class ArtistAlbum {

    @SerializedName("id")
    @Expose
    val id: Int? = null

    @SerializedName("profiles")
    @Expose
    val profiles: List<ProfileImage>? = null
}