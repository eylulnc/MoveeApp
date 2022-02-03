package com.eylulcan.moviefragment.domain.entity

data class MovieDetailEntity (
    val adult: Boolean,
    val backdropPath: String,
    val belongsToCollectionEntity: BelongsToCollectionEntity,
    val budget: Long,
    val genreEntities: List<GenreEntity>,
    val homepage: String,
    val id: Int,
    val imdbId: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val productionCompanyEntities: List<ProductionCountryEntity>,
    val productionCountryEntities: List<ProductionCountryEntity>,
    val releaseDate: String,
    val revenue: Long,
    val runtime: Long,
    val spokenLanguageEntities: List<SpokenLanguageEntity>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Long

)