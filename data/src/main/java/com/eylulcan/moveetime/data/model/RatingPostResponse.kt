package com.eylulcan.moveetime.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

@Generated("jsonschema2pojo")
class RatingPostResponse {

    @SerializedName("success")
    @Expose
    val success: Boolean? = null

    @SerializedName("status_code")
    @Expose
    val statusCode: Int? = null

    @SerializedName("status_message")
    @Expose
    val statusMessage: String? = null
}