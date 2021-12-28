package com.eylulcan.moviefragment.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

@Generated("jsonschema2pojo")
class GuestSession {

    @SerializedName("success")
    @Expose
    private var success: Boolean? = null

    @SerializedName("guest_session_id")
    @Expose
    var sessionID: String? = null
}