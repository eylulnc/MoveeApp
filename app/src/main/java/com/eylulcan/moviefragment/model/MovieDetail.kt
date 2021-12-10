package com.eylulcan.moviefragment.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

@Generated("jsonschema2pojo")
class MovieDetail {
    @SerializedName("adult")
    @Expose
    private val adult: Boolean? = null

    @SerializedName("backdrop_path")
    @Expose
    private val backdropPath: String? = null

    @SerializedName("belongs_to_collection")
    @Expose
    private val belongsToCollection: BelongsToCollection? = null

    @SerializedName("budget")
    @Expose
    private val budget: Int? = null

    @SerializedName("genres")
    @Expose
    private val genres: List<Genre>? = null

    @SerializedName("homepage")
    @Expose
    private val homepage: String? = null

    @SerializedName("id")
    @Expose
    private val id: Int? = null

    @SerializedName("imdb_id")
    @Expose
    private val imdbId: String? = null

    @SerializedName("original_language")
    @Expose
    private val originalLanguage: String? = null

    @SerializedName("original_title")
    @Expose
    private val originalTitle: String? = null

    @SerializedName("overview")
    @Expose
    private val overview: String? = null

    @SerializedName("popularity")
    @Expose
    private val popularity: Double? = null

    @SerializedName("poster_path")
    @Expose
    private val posterPath: String? = null

    @SerializedName("production_companies")
    @Expose
    private val productionCompanies: List<ProductionCountry>? = null

    @SerializedName("production_countries")
    @Expose
    private val productionCountries: List<ProductionCountry>? = null

    @SerializedName("release_date")
    @Expose
    private val releaseDate: String? = null

    @SerializedName("revenue")
    @Expose
    private val revenue: Int? = null

    @SerializedName("runtime")
    @Expose
    private val runtime: Int? = null

    @SerializedName("spoken_languages")
    @Expose
    private val spokenLanguages: List<SpokenLanguage>? = null

    @SerializedName("status")
    @Expose
    private val status: String? = null

    @SerializedName("tagline")
    @Expose
    private val tagline: String? = null

    @SerializedName("title")
    @Expose
    private val title: String? = null

    @SerializedName("video")
    @Expose
    private val video: Boolean? = null

    @SerializedName("vote_average")
    @Expose
    private val voteAverage: Double? = null

    @SerializedName("vote_count")
    @Expose
    private val voteCount: Int? = null

}