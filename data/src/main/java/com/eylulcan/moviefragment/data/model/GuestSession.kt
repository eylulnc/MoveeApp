package com.eylulcan.moviefragment.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

@Generated("jsonschema2pojo")
class GuestSession {

    @SerializedName("success")
    @Expose
    val success: Boolean? = null

    @SerializedName("guest_session_id")
    @Expose
    val sessionID: String? = null
}