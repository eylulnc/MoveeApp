package com.eylulcan.moviefragment.data.model

import com.eylulcan.moviefragment.domain.entity.KnownForEntity
import javax.annotation.Generated
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Generated("jsonschema2pojo")
class PeopleResult {
    @SerializedName("adult")
    @Expose
    private val adult: Boolean? = null

    @SerializedName("gender")
    @Expose
    private val gender: Int? = null

    @SerializedName("id")
    @Expose
    val id: Int? = null

    @SerializedName("known_for")
    @Expose
    private val knownForEntity: List<KnownForEntity>? = null

    @SerializedName("known_for_department")
    @Expose
    val knownForDepartment: String? = null

    @SerializedName("name")
    @Expose
    val name: String? = null

    @SerializedName("popularity")
    @Expose
    private val popularity: Double? = null

    @SerializedName("profile_path")
    @Expose
    val profilePath: String? = null
}