package com.eylulcan.moviefragment.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

@Generated("jsonschema2pojo")
class RatingPostResponse {

    @SerializedName("success")
    @Expose
    private val success: Boolean? = null

    @SerializedName("status_code")
    @Expose
    private val statusCode: Int? = null

    @SerializedName("status_message")
    @Expose
    val statusMessage: String? = null
}