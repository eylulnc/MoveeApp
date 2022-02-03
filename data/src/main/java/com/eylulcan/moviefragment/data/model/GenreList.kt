package com.eylulcan.moviefragment.data.model

import com.eylulcan.moviefragment.domain.entity.GenreEntity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

@Generated("jsonschema2pojo")
 class GenreList {
    @SerializedName("genres")
    @Expose
    val genreEntities :List<GenreEntity>? = null
}
