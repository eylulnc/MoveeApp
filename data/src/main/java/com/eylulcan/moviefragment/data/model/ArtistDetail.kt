package com.eylulcan.moviefragment.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

@Generated("jsonschema2pojo")
class ArtistDetail {

    @SerializedName("adult")
    @Expose
    val adult: Boolean? = null

    @SerializedName("also_known_as")
    @Expose
    val alsoKnownAs: List<String>? = null

    @SerializedName("biography")
    @Expose
    val biography: String? = null

    @SerializedName("birthday")
    @Expose
    val birthday: String? = null

    @SerializedName("deathday")
    @Expose
    val deathday: String? = null

    @SerializedName("gender")
    @Expose
    val gender: Int? = null

    @SerializedName("homepage")
    @Expose
    val homepage: Any? = null

    @SerializedName("id")
    @Expose
    val id: Int? = null

    @SerializedName("imdb_id")
    @Expose
    val imdbId: String? = null

    @SerializedName("known_for_department")
    @Expose
    val knownForDepartment: String? = null

    @SerializedName("name")
    @Expose
    val name: String? = null

    @SerializedName("place_of_birth")
    @Expose
    val placeOfBirth: String? = null

    @SerializedName("popularity")
    @Expose
    val popularity: Double? = null

    @SerializedName("profile_path")
    @Expose
    val profilePath: String? = null
}