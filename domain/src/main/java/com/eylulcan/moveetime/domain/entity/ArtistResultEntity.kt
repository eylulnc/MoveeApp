package com.eylulcan.moveetime.domain.entity

data class ArtistResultEntity(
    val adult: Boolean? = null,
    val gender: Int? = null,
    val id: Int? = null,
    val knownFor: List<KnownForEntity>? = null,
    val knownForDepartment: String? = null,
    val name: String? = null,
    val popularity: Double? = null,
    val profilePath: String? = null
)