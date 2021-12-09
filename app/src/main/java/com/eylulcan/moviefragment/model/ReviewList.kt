package com.eylulcan.moviefragment.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

@Generated("jsonschema2pojo")
class ReviewList {
    @SerializedName("id")
    @Expose
    private val id: Int? = null

    @SerializedName("page")
    @Expose
    private val page: Int? = null

    @SerializedName("results")
    @Expose
    private val results: List<Review>? = null

    @SerializedName("total_pages")
    @Expose
    private val totalPages: Int? = null

    @SerializedName("total_results")
    @Expose
    private val totalResults: Int? = null
}