package com.eylulcan.moviefragment.data.mapper

import com.eylulcan.moviefragment.data.Utils.IF_BOOLEAN_NULL
import com.eylulcan.moviefragment.data.Utils.IF_INT_NULL
import com.eylulcan.moviefragment.data.Utils.IF_STR_NULL
import com.eylulcan.moviefragment.data.Utils.If_DBL_NULL
import com.eylulcan.moviefragment.data.model.ArtistAlbum
import com.eylulcan.moviefragment.data.model.ArtistDetail
import com.eylulcan.moviefragment.data.model.ArtistList
import com.eylulcan.moviefragment.data.model.ArtistMovieCredits
import com.eylulcan.moviefragment.domain.entity.*
import javax.inject.Inject

class ArtistMapper @Inject constructor() {

    fun convertToArtistAlbumEntity(artistAlbum: ArtistAlbum): ArtistAlbumEntity {
        val album = arrayListOf<ProfileImageEntity>()
        artistAlbum.artistProfileImageEntities?.forEach{
            album.add(it)
        }
        return ArtistAlbumEntity(
                id = artistAlbum.id ?: IF_INT_NULL,
                artistProfileImages = album
            )
    }

    fun convertToArtistListEntity(artistList: ArtistList): ArtistListEntity {
        val artistResultList = arrayListOf<ArtistResultEntity>()
        artistList.results?.forEach {
            artistResultList.add(it)
        }
        return ArtistListEntity(
            page = artistList.page ?: IF_INT_NULL,
            results = artistResultList,
            totalPages = artistList.totalPages ?: IF_INT_NULL,
            totalResults = artistList.totalResults ?: IF_INT_NULL
        )
    }

    fun convertToArtistDetailEntity(artistDetail: ArtistDetail): ArtistDetailEntity {
        val alsoKnownAsList = arrayListOf<String>()
        artistDetail.alsoKnownAs?.forEach{
            alsoKnownAsList.add(it)
        }
        return ArtistDetailEntity(
            adult = artistDetail.adult ?: IF_BOOLEAN_NULL,
            alsoKnownAs = alsoKnownAsList,
            biography = artistDetail.biography ?: IF_STR_NULL,
            birthday = artistDetail.birthday ?: IF_STR_NULL,
            deathday = artistDetail.deathday ?: IF_STR_NULL,
            gender = artistDetail.gender ?: IF_INT_NULL,
            homepage = artistDetail.homepage ?: IF_STR_NULL,
            id = artistDetail.id ?: IF_INT_NULL,
            imdbId = artistDetail.imdbId ?: IF_STR_NULL,
            knownForDepartment = artistDetail.knownForDepartment ?: IF_STR_NULL,
            name = artistDetail.name ?: IF_STR_NULL,
            placeOfBirth = artistDetail.placeOfBirth ?: IF_STR_NULL,
            popularity = artistDetail.popularity ?: If_DBL_NULL,
            profilePath = artistDetail.profilePath ?: IF_STR_NULL

        )
    }

    fun convertToMovieCreditsEntity(artistMovieCredits: ArtistMovieCredits): ArtistMovieCreditsEntity {
        val castList = arrayListOf<CastEntity>()
        val crewList = arrayListOf<CrewEntity>()
        artistMovieCredits.cast?.forEach {
             castList.add(it)
        }
        artistMovieCredits.crew?.forEach {
            crewList.add(it)
        }
        return ArtistMovieCreditsEntity(
            cast = castList,
            crew = crewList,
            id = artistMovieCredits.id ?: IF_INT_NULL
        )
    }



}
