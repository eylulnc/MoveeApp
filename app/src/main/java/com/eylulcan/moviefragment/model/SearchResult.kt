package com.eylulcan.moviefragment.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SearchResult {

    @SerializedName("")
    @Expose
    val data: Any? = null

    @SerializedName("media_type")
    @Expose
    val mediaType: String? = null
}