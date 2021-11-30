package com.eylulcan.moviefragment.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

@Generated("jsonschema2pojo")
 class GenreList {
    @SerializedName("genres")
    @Expose
    var genres :List<Genre>? = null
}
