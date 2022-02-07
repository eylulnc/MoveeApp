package com.eylulcan.moviefragment.data.model

import com.eylulcan.moviefragment.domain.entity.ReviewEntity
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

@Generated("jsonschema2pojo")
class ReviewList {
    @SerializedName("id")
    @Expose
    val id: Int? = null

    @SerializedName("page")
    @Expose
    val page: Int? = null

    @SerializedName("results")
    @Expose
    val results: List<ReviewEntity>? = null

    @SerializedName("total_pages")
    @Expose
    val totalPages: Int? = null

    @SerializedName("total_results")
    @Expose
    val totalResults: Int? = null
}