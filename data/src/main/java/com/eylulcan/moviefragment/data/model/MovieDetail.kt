package com.eylulcan.moviefragment.data.model

import com.eylulcan.moviefragment.domain.entity.BelongsToCollectionEntity
import com.eylulcan.moviefragment.domain.entity.GenreEntity
import com.eylulcan.moviefragment.domain.entity.ProductionCountryEntity
import com.eylulcan.moviefragment.domain.entity.SpokenLanguageEntity
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
    val backdropPath: String? = null

    @SerializedName("belongs_to_collection")
    @Expose
    private val belongsToCollectionEntity: BelongsToCollectionEntity? = null

    @SerializedName("budget")
    @Expose
    private val budget: Long? = null

    @SerializedName("genres")
    @Expose
    val genreEntities: List<GenreEntity>? = null

    @SerializedName("homepage")
    @Expose
    private val homepage: String? = null

    @SerializedName("id")
    @Expose
    val id: Int? = null

    @SerializedName("imdb_id")
    @Expose
    private val imdbId: String? = null

    @SerializedName("original_language")
    @Expose
    val originalLanguage: String? = null

    @SerializedName("original_title")
    @Expose
    private val originalTitle: String? = null

    @SerializedName("overview")
    @Expose
    val overview: String? = null

    @SerializedName("popularity")
    @Expose
    private val popularity: Double? = null

    @SerializedName("poster_path")
    @Expose
    val posterPath: String? = null

    @SerializedName("production_companies")
    @Expose
    private val productionCompanyEntities: List<ProductionCountryEntity>? = null

    @SerializedName("production_countries")
    @Expose
    private val productionCountryEntities: List<ProductionCountryEntity>? = null

    @SerializedName("release_date")
    @Expose
    val releaseDate: String? = null

    @SerializedName("revenue")
    @Expose
    private val revenue: Long? = null

    @SerializedName("runtime")
    @Expose
    val runtime: Long? = null

    @SerializedName("spoken_languages")
    @Expose
    val spokenLanguageEntities: List<SpokenLanguageEntity>? = null

    @SerializedName("status")
    @Expose
    private val status: String? = null

    @SerializedName("tagline")
    @Expose
    private val tagline: String? = null

    @SerializedName("title")
    @Expose
    val title: String? = null

    @SerializedName("video")
    @Expose
    val video: Boolean? = null

    @SerializedName("vote_average")
    @Expose
    val voteAverage: Double? = null

    @SerializedName("vote_count")
    @Expose
    private val voteCount: Long? = null

}