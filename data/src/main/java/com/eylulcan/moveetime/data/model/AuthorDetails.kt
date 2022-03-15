package com.eylulcan.moveetime.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

@Generated("jsonschema2pojo")
class AuthorDetails {
    @SerializedName("name")
    @Expose
    val name: String? = null

    @SerializedName("username")
    @Expose
    val username: String? = null

    @SerializedName("avatar_path")
    @Expose
    val avatarPath: String? = null

    @SerializedName("rating")
    @Expose
    val rating: Double? = null
}