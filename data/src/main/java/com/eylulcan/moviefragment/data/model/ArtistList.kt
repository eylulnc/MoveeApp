package com.eylulcan.moviefragment.data.model

import com.eylulcan.moviefragment.domain.entity.ArtistResultEntity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ArtistList {

    @SerializedName("page")
    @Expose
    val page: Int? = null

    @SerializedName("results")
    @Expose
    val resultEntities: List<ArtistResultEntity>? = null

    @SerializedName("total_pages")
    @Expose
    val totalPages: Int? = null

    @SerializedName("total_results")
    @Expose
    val totalResults: Int? = null
}