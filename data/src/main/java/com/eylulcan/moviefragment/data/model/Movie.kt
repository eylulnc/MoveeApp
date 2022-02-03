package com.eylulcan.moviefragment.data.model

import com.eylulcan.moviefragment.domain.entity.ResultMovieEntity
import javax.annotation.Generated
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Generated("jsonschema2pojo")
class Movie: Serializable {

    @SerializedName("page")
    @Expose
    private val page: Int? = null

    @SerializedName("results")
    @Expose
    val resultEntities: List<ResultMovieEntity>? = null

    @SerializedName("total_pages")
    @Expose
    private val totalPages: Int? = null

    @SerializedName("total_results")
    @Expose
    private val totalResults: Int? = null

}