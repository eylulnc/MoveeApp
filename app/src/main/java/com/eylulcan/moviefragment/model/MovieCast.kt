package com.eylulcan.moviefragment.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

@Generated("jsonschema2pojo")
class MovieCast {
    @SerializedName("adult")
    @Expose
    private val adult: Boolean? = null

    @SerializedName("gender")
    @Expose
    private val gender: Int? = null

    @SerializedName("id")
    @Expose
    val id: Int? = null

    @SerializedName("known_for_department")
    @Expose
    private val knownForDepartment: String? = null

    @SerializedName("name")
    @Expose
    val name: String? = null

    @SerializedName("original_name")
    @Expose
    private val originalName: String? = null

    @SerializedName("popularity")
    @Expose
    private val popularity: Double? = null

    @SerializedName("profile_path")
    @Expose
    val profilePath: String? = null

    @SerializedName("cast_id")
    @Expose
    private val castId: Int? = null

    @SerializedName("character")
    @Expose
    val character: String? = null

    @SerializedName("credit_id")
    @Expose
    private val creditId: String? = null

    @SerializedName("order")
    @Expose
    private val order: Int? = null
}