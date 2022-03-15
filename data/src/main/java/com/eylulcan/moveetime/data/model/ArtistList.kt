package com.eylulcan.moveetime.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ArtistList {

    @SerializedName("page")
    @Expose
    val page: Int? = null

    @SerializedName("results")
    @Expose
    val results: List<ArtistResult>? = null

    @SerializedName("total_pages")
    @Expose
    val totalPages: Int? = null

    @SerializedName("total_results")
    @Expose
    val totalResults: Int? = null
}