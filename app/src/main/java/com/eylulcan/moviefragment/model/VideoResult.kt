package com.eylulcan.moviefragment.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

@Generated("jsonschema2pojo")
class VideoResult {
    @SerializedName("iso_639_1")
    @Expose
    private val iso6391: String? = null

    @SerializedName("iso_3166_1")
    @Expose
    private val iso31661: String? = null

    @SerializedName("name")
    @Expose
    private val name: String? = null

    @SerializedName("key")
    @Expose
    private val key: String? = null

    @SerializedName("site")
    @Expose
    private val site: String? = null

    @SerializedName("size")
    @Expose
    private val size: Int? = null

    @SerializedName("type")
    @Expose
    private val type: String? = null

    @SerializedName("official")
    @Expose
    private val official: Boolean? = null

    @SerializedName("published_at")
    @Expose
    private val publishedAt: String? = null

    @SerializedName("id")
    @Expose
    private val id: String? = null

}