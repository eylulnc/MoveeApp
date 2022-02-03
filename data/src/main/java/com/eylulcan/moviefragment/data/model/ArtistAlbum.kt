package com.eylulcan.moviefragment.data.model

import com.eylulcan.moviefragment.domain.entity.ProfileImageEntity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import javax.annotation.Generated


@Generated("jsonschema2pojo")
class ArtistAlbum : Serializable{

    @SerializedName("id")
    @Expose
    val id: Int? = null

    @SerializedName("profiles")
    @Expose
    val artistProfileImageEntities: List<ProfileImageEntity>? = null
}