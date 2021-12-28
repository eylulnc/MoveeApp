package com.eylulcan.moviefragment.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

@Generated("jsonschema2pojo")
class PostRatingBody {

    @SerializedName("value")
    @Expose
    var ratingValue: Double? = null

}