package com.eylulcan.moviefragment.data.model

import com.eylulcan.moviefragment.domain.entity.SearchResultEntity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

@Generated("jsonschema2pojo")
class SearchResultList {

    @SerializedName("page")
    @Expose
    private val page: Int? = null

    @SerializedName("results")
    @Expose
    val searchResultEntities: List<SearchResultEntity>? = null

    @SerializedName("total_pages")
    @Expose
    private val totalPages: Int? = null

    @SerializedName("total_results")
    @Expose
    private val totalResults: Int? = null
}