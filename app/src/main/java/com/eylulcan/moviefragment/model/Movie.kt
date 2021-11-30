package com.eylulcan.moviefragment.model

import javax.annotation.Generated
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Generated("jsonschema2pojo")
class Movie: Serializable {

    @SerializedName("page")
    @Expose
    private var page: Int? = null

    @SerializedName("results")
    @Expose
    var results: List<ResultMovie>? = null

    @SerializedName("total_pages")
    @Expose
    private var totalPages: Int? = null

    @SerializedName("total_results")
    @Expose
    private var totalResults: Int? = null

}