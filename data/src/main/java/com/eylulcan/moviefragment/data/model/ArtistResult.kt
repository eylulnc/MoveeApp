package com.eylulcan.moviefragment.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

@Generated("jsonschema2pojo")
class ArtistResult {
    @SerializedName("adult")
    @Expose
    val adult: Boolean? = null

    @SerializedName("gender")
    @Expose
    val gender: Int? = null

    @SerializedName("id")
    @Expose
    val id: Int? = null

    @SerializedName("known_for")
    @Expose
    val knownFor: List<KnownFor>? = null

    @SerializedName("known_for_department")
    @Expose
    val knownForDepartment: String? = null

    @SerializedName("name")
    @Expose
    val name: String? = null

    @SerializedName("popularity")
    @Expose
    val popularity: Double? = null

    @SerializedName("profile_path")
    @Expose
    val profilePath: String? = null
}