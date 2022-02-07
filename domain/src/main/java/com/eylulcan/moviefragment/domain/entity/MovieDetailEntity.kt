package com.eylulcan.moviefragment.domain.entity

data class MovieDetailEntity(
    val adult: Boolean,
    val backdropPath: String,
    val belongsToCollection: BelongsToCollectionEntity,
    val budget: Long,
    val genres: List<GenreEntity>,
    val homepage: String,
    val id: Int,
    val imdbId: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val productionCompany: List<ProductionCountryEntity>,
    val productionCountry: List<ProductionCountryEntity>,
    val releaseDate: String,
    val revenue: Long,
    val runtime: Long,
    val spokenLanguages: List<SpokenLanguageEntity>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Long

)