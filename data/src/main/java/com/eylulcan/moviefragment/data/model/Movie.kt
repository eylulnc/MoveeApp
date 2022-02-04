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
    val page: Int? = null

    @SerializedName("results")
    @Expose
    val results: List<ResultMovieEntity>? = null

    @SerializedName("total_pages")
    @Expose
    val totalPages: Int? = null

    @SerializedName("total_results")
    @Expose
    val totalResults: Int? = null

}