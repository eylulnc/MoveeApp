package com.eylulcan.moviefragment.domain.entity

data class PeopleResultEntity(
    val adult: Boolean? = null,
    val gender: Int? = null,
    val id: Int? = null,
    val knownForEntity: List<KnownForEntity>? = null,
    val knownForDepartment: String? = null,
    val name: String? = null,
    val popularity: Double? = null,
    val profilePath: String? = null
)