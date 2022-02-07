package com.eylulcan.moviefragment.data.mapper

import com.eylulcan.moviefragment.data.Utils.IF_BOOLEAN_NULL
import com.eylulcan.moviefragment.data.Utils.IF_DBL_NULL
import com.eylulcan.moviefragment.data.Utils.IF_INT_NULL
import com.eylulcan.moviefragment.data.Utils.IF_STR_NULL
import com.eylulcan.moviefragment.data.model.*
import com.eylulcan.moviefragment.domain.entity.*
import javax.inject.Inject

class ArtistMapper @Inject constructor() {

    fun convertToArtistAlbumEntity(artistAlbum: ArtistAlbum): ArtistAlbumEntity {
        val album = arrayListOf<ProfileImageEntity>()
        artistAlbum.artistProfileImages?.forEach {
            album.add(convertProfileImageEntity(it))
        }
        return ArtistAlbumEntity(
            id = artistAlbum.id ?: IF_INT_NULL,
            artistProfileImages = album
        )
    }

    private fun convertProfileImageEntity(profileImage: ProfileImage): ProfileImageEntity {
        return ProfileImageEntity(
            aspectRatio = profileImage.aspectRatio ?: IF_DBL_NULL,
            height = profileImage.height ?: IF_INT_NULL,
            iso6391 = profileImage.iso6391 ?: IF_INT_NULL,
            filePath = profileImage.filePath ?: IF_STR_NULL,
            voteCount = profileImage.voteCount ?: IF_INT_NULL,
            voteAverage = profileImage.voteAverage ?: IF_DBL_NULL,
            width = profileImage.width ?: IF_INT_NULL
        )
    }

    fun convertToArtistListEntity(artistList: ArtistList): ArtistListEntity {
        val artistResultList = arrayListOf<ArtistResultEntity>()
        artistList.results?.forEach {
            artistResultList.add(convertToArtistResultEntity(it))
        }
        return ArtistListEntity(
            page = artistList.page ?: IF_INT_NULL,
            results = artistResultList,
            totalPages = artistList.totalPages ?: IF_INT_NULL,
            totalResults = artistList.totalResults ?: IF_INT_NULL
        )
    }

    private fun convertToArtistResultEntity(artistResult: ArtistResult): ArtistResultEntity {
        val knownForList = arrayListOf<KnownForEntity>()
        artistResult.knownFor?.forEach {
            knownForList.add(convertKnownForEntity(it))
        }
        return ArtistResultEntity(
            adult = artistResult.adult ?: IF_BOOLEAN_NULL,
            gender = artistResult.gender ?: IF_INT_NULL,
            id = artistResult.id ?: IF_INT_NULL,
            knownFor = knownForList,
            knownForDepartment = artistResult.knownForDepartment ?: IF_STR_NULL,
            name = artistResult.name ?: IF_STR_NULL,
            popularity = artistResult.popularity ?: IF_DBL_NULL,
            profilePath = artistResult.profilePath ?: IF_STR_NULL

        )
    }

    private fun convertKnownForEntity(knownFor: KnownFor): KnownForEntity {
        val genreIdList = arrayListOf<Int>()
        knownFor.genreIds?.forEach {
            genreIdList.add(it)
        }
        val originCountries = arrayListOf<String>()
        knownFor.originCountry?.forEach {
            originCountries.add(it)
        }
        return KnownForEntity(
            adult = knownFor.adult ?: IF_BOOLEAN_NULL,
            backdropPath = knownFor.backdropPath ?: IF_STR_NULL,
            genreIds = genreIdList,
            id = knownFor.id ?: IF_INT_NULL,
            mediaType = knownFor.mediaType ?: IF_STR_NULL,
            originalLanguage = knownFor.originalLanguage ?: IF_STR_NULL,
            originalTitle = knownFor.originalTitle ?: IF_STR_NULL,
            overview = knownFor.overview ?: IF_STR_NULL,
            posterPath = knownFor.posterPath ?: IF_STR_NULL,
            releaseDate = knownFor.releaseDate ?: IF_STR_NULL,
            title = knownFor.title ?: IF_STR_NULL,
            video = knownFor.video ?: IF_BOOLEAN_NULL,
            voteAverage = knownFor.voteAverage ?: IF_DBL_NULL,
            voteCount = knownFor.voteCount ?: IF_INT_NULL,
            firstAirDate = knownFor.firstAirDate ?: IF_STR_NULL,
            name = knownFor.name ?: IF_STR_NULL,
            originCountry = originCountries,
            originalName = knownFor.originalName ?: IF_STR_NULL

        )
    }

    fun convertToArtistDetailEntity(artistDetail: ArtistDetail): ArtistDetailEntity {
        val alsoKnownAsList = arrayListOf<String>()
        artistDetail.alsoKnownAs?.forEach {
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
            popularity = artistDetail.popularity ?: IF_DBL_NULL,
            profilePath = artistDetail.profilePath ?: IF_STR_NULL

        )
    }

    fun convertToMovieCreditsEntity(artistMovieCredits: ArtistMovieCredits): ArtistMovieCreditsEntity {
        val castList = arrayListOf<CastEntity>()
        val crewList = arrayListOf<CrewEntity>()
        artistMovieCredits.cast?.forEach {
            castList.add(convertToMovieCastEntity(it))
        }
        artistMovieCredits.crew?.forEach {
            crewList.add(convertToMovieCrewEntity(it))
        }
        return ArtistMovieCreditsEntity(
            cast = castList,
            crew = crewList,
            id = artistMovieCredits.id ?: IF_INT_NULL
        )
    }

    private fun convertToMovieCastEntity(cast: Cast): CastEntity {
        val genreIdList = arrayListOf<Int>()
        cast.genreIds?.forEach {
            genreIdList.add(it)
        }
        return CastEntity(
            id = cast.id ?: IF_INT_NULL,
            originalTitle = cast.originalTitle ?: IF_STR_NULL,
            posterPath = cast.posterPath ?: IF_STR_NULL,
            video = cast.video ?: IF_BOOLEAN_NULL,
            voteAverage = cast.voteAverage ?: IF_DBL_NULL,
            overview = cast.overview ?: IF_STR_NULL,
            popularity = cast.popularity ?: IF_DBL_NULL,
            releaseDate = cast.releaseDate ?: IF_STR_NULL,
            voteCount = cast.voteCount ?: IF_INT_NULL,
            title = cast.title ?: IF_STR_NULL,
            adult = cast.adult ?: IF_BOOLEAN_NULL,
            backdropPath = cast.backdropPath ?: IF_STR_NULL,
            originalLanguage = cast.originalLanguage ?: IF_STR_NULL,
            genreIds = genreIdList,
            character = cast.character ?: IF_STR_NULL,
            creditId = cast.creditId ?: IF_STR_NULL,
            order = cast.order ?: IF_INT_NULL
        )
    }

    private fun convertToMovieCrewEntity(crew: Crew): CrewEntity {
        val genreIdList = arrayListOf<Int>()
        crew.genreIds?.forEach {
            genreIdList.add(it)
        }
        return CrewEntity(
            adult = crew.adult ?: IF_BOOLEAN_NULL,
            backdropPath = crew.backdropPath ?: IF_STR_NULL,
            genreIds = genreIdList,
            id = crew.id ?: IF_INT_NULL,
            originalLanguage = crew.originalLanguage ?: IF_STR_NULL,
            originalTitle = crew.originalTitle ?: IF_STR_NULL,
            overview = crew.overview ?: IF_STR_NULL,
            posterPath = crew.posterPath ?: IF_STR_NULL,
            releaseDate = crew.releaseDate ?: IF_STR_NULL,
            title = crew.title ?: IF_STR_NULL,
            video = crew.video ?: IF_BOOLEAN_NULL,
            voteAverage = crew.voteAverage ?: IF_DBL_NULL,
            voteCount = crew.voteCount ?: IF_INT_NULL,
            popularity = crew.popularity ?: IF_DBL_NULL,
            creditId = crew.creditId ?: IF_STR_NULL,
            department = crew.department ?: IF_STR_NULL,
            job = crew.job ?: IF_STR_NULL
        )
    }

}
