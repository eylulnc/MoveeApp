package com.eylulcan.moviefragment.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

@Generated("jsonschema2pojo")
class AuthorDetails {
    @SerializedName("name")
    @Expose
    private val name: String? = null

    @SerializedName("username")
    @Expose
    private val username: String? = null

    @SerializedName("avatar_path")
    @Expose
    val avatarPath: Any? = null

    @SerializedName("rating")
    @Expose
    val rating: Double? = null
}