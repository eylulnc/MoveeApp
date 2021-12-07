package com.eylulcan.moviefragment.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

@Generated("jsonschema2pojo")
class ArtistDetail {

    @SerializedName("adult")
    @Expose
    private val adult: Boolean? = null

    @SerializedName("also_known_as")
    @Expose
    private val alsoKnownAs: List<String>? = null

    @SerializedName("biography")
    @Expose
    val biography: String? = null

    @SerializedName("birthday")
    @Expose
    val birthday: String? = null

    @SerializedName("deathday")
    @Expose
    private val deathday: Any? = null

    @SerializedName("gender")
    @Expose
    private val gender: Int? = null

    @SerializedName("homepage")
    @Expose
    private val homepage: Any? = null

    @SerializedName("id")
    @Expose
    private val id: Int? = null

    @SerializedName("imdb_id")
    @Expose
    private val imdbId: String? = null

    @SerializedName("known_for_department")
    @Expose
    val knownForDepartment: String? = null

    @SerializedName("name")
    @Expose
    val name: String? = null

    @SerializedName("place_of_birth")
    @Expose
    private val placeOfBirth: String? = null

    @SerializedName("popularity")
    @Expose
    private val popularity: Double? = null

    @SerializedName("profile_path")
    @Expose
    val profilePath: String? = null
}