package com.eylulcan.moviefragment.data.mapper

import com.eylulcan.moviefragment.data.Utils.IF_BOOLEAN_NULL
import com.eylulcan.moviefragment.data.Utils.IF_DBL_NULL
import com.eylulcan.moviefragment.data.Utils.IF_INT_NULL
import com.eylulcan.moviefragment.data.Utils.IF_LONG_NULL
import com.eylulcan.moviefragment.data.Utils.IF_STR_NULL
import com.eylulcan.moviefragment.data.model.*
import com.eylulcan.moviefragment.domain.entity.*

class MovieMapper {

    fun convertToMovieEntity(movie: Movie): MovieEntity {
        val list : ArrayList<ResultMovieEntity> = arrayListOf()
        movie.results?.forEach {
            list.add(it)
        }
        return MovieEntity(
            page = movie.page ?: IF_INT_NULL,
            results = list,
            totalPages = movie.totalPages ?: IF_INT_NULL ,
            totalResults = movie.totalResults ?: IF_INT_NULL
        )
    }

    fun convertToGuestSessionEntity(guestSession: GuestSession): GuestSessionEntity {
        return GuestSessionEntity(
            success = guestSession.success ?: IF_BOOLEAN_NULL,
            sessionID = guestSession.sessionID ?: IF_STR_NULL
        )
    }

    fun convertToRatingPostResponseEntity(ratingPostResponse: RatingPostResponse): RatingPostResponseEntity {
        return RatingPostResponseEntity(
            success = ratingPostResponse.success ?: IF_BOOLEAN_NULL,
            statusCode = ratingPostResponse.statusCode ?: IF_INT_NULL,
            statusMessage = ratingPostResponse.statusMessage ?: IF_STR_NULL
        )
    }

    fun convertToMovieCreditsEntity(movieCredits: MovieCredits): MovieCreditsEntity {
        val cast = arrayListOf<MovieCastEntity>()
        movieCredits.cast?.forEach {
            cast.add(it)
        }
        return MovieCreditsEntity(
            id = movieCredits.id ?: IF_INT_NULL,
            cast = cast
        )
    }

    fun convertToReviewListEntity(reviewList: ReviewList): ReviewListEntity {
        val reviews = arrayListOf<ReviewEntity>()
        reviewList.results?.forEach {
            reviews.add(it)
        }
        return ReviewListEntity(
            id = reviewList.id ?: IF_INT_NULL,
            page = reviewList.page ?: IF_INT_NULL,
            results = reviews,
            totalPages = reviewList.totalPages ?: IF_INT_NULL,
            totalResults = reviewList.totalResults ?: IF_INT_NULL
        )
    }

    fun convertToVideoListEntity(videoList: VideoList): VideoListEntity {
        val videos = arrayListOf<VideoResultEntity>()
        videoList.results?.forEach {
            videos.add(it)
        }
        return VideoListEntity(
            id = videoList.id ?: IF_INT_NULL,
            results = videos
        )
    }

    fun convertToMovieDetailEntity(movieDetail: MovieDetail): MovieDetailEntity {
        val productionCompanyList = arrayListOf<ProductionCountryEntity>()
        movieDetail.productionCompany?.forEach {
            productionCompanyList.add(it)
        }
        val productionCountryList = arrayListOf<ProductionCountryEntity>()
        movieDetail.productionCountry?.forEach {
            productionCountryList.add(it)
        }
        val languages = arrayListOf<SpokenLanguageEntity>()
        movieDetail.spokenLanguages?.forEach {
            languages.add(it)
        }
        val genresList = arrayListOf<GenreEntity>()
        movieDetail.genres?.forEach {
            genresList.add(it)
        }
        return MovieDetailEntity(
            adult = movieDetail.adult ?: IF_BOOLEAN_NULL,
            backdropPath = movieDetail.backdropPath ?: IF_STR_NULL,
            belongsToCollection = movieDetail.belongsToCollection as BelongsToCollectionEntity,
            budget = movieDetail.budget ?: IF_LONG_NULL,
            genres = genresList,
            homepage = movieDetail.homepage ?: IF_STR_NULL,
            id = movieDetail.id ?: IF_INT_NULL,
            imdbId = movieDetail.imdbId ?: IF_STR_NULL,
            originalLanguage = movieDetail.originalLanguage ?: IF_STR_NULL,
            originalTitle = movieDetail.originalTitle ?: IF_STR_NULL,
            overview = movieDetail.overview ?: IF_STR_NULL,
            popularity = movieDetail.popularity ?: IF_DBL_NULL,
            posterPath = movieDetail.posterPath ?: IF_STR_NULL,
            productionCompany = productionCompanyList,
            productionCountry = productionCountryList,
            releaseDate = movieDetail.releaseDate ?: IF_STR_NULL,
            revenue = movieDetail.revenue ?: IF_LONG_NULL,
            runtime = movieDetail.runtime ?: IF_LONG_NULL,
            spokenLanguages = languages,
            status = movieDetail.status ?: IF_STR_NULL,
            tagline =  movieDetail.tagline ?: IF_STR_NULL,
            title = movieDetail.title ?: IF_STR_NULL,
            video = movieDetail.video ?: IF_BOOLEAN_NULL,
            voteAverage = movieDetail.voteAverage ?: IF_DBL_NULL,
            voteCount = movieDetail.voteCount ?: IF_LONG_NULL
        )
    }

}