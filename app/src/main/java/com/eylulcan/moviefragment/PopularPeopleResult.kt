package com.eylulcan.moviefragment

import javax.annotation.Generated
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Generated("jsonschema2pojo")
class PopularPeopleResult {
    @SerializedName("adult")
    @Expose
    private val adult: Boolean? = null

    @SerializedName("gender")
    @Expose
    private val gender: Int? = null

    @SerializedName("id")
    @Expose
    private val id: Int? = null

    //@SerializedName("known_for")
    //@Expose
    //private val knownFor: List<KnownFor>? = null
    //KnowFor model is not created

    @SerializedName("known_for_department")
    @Expose
    private val knownForDepartment: String? = null

    @SerializedName("name")
    @Expose
    val name: String? = null

    @SerializedName("popularity")
    @Expose
    private val popularity: Double? = null

    @SerializedName("profile_path")
    @Expose
    private val profilePath: String? = null
}