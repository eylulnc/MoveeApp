package com.eylulcan.moveetime.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SearchResult {

    @SerializedName("id")
    @Expose
    val id: Int? = null

    @SerializedName("name")
    @Expose
    val name: String? = null

    @SerializedName("poster_path")
    @Expose
    val posterPath: String? = null

    @SerializedName("profile_path")
    @Expose
    val profilePath: String? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("media_type")
    @Expose
    val mediaType: String? = null
}