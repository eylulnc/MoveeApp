package com.eylulcan.moviefragment

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PopularPeopleList {

    @SerializedName("page")
    @Expose
    private val page: Int? = null

    @SerializedName("results")
    @Expose
    val results: List<PopularPeopleResult>? = null

    @SerializedName("total_pages")
    @Expose
    private val totalPages: Int? = null

    @SerializedName("total_results")
    @Expose
    private val totalResults: Int? = null
}