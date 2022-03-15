package com.eylulcan.moveetime.data.mapper

import com.eylulcan.moveetime.data.Utils.IF_BOOLEAN_NULL
import com.eylulcan.moveetime.data.Utils.IF_DBL_NULL
import com.eylulcan.moveetime.data.Utils.IF_INT_NULL
import com.eylulcan.moveetime.data.Utils.IF_LONG_NULL
import com.eylulcan.moveetime.data.Utils.IF_STR_NULL
import com.eylulcan.moveetime.data.model.*
import com.eylulcan.moveetime.domain.entity.*
import javax.inject.Inject

class MovieMapper @Inject constructor() {

    fun convertToMovieEntity(movie: Movie): MovieEntity {
        val list: ArrayList<ResultMovieEntity> = arrayListOf()
        movie.results?.forEach {
            list.add(convertToResultMovieEntity(it))
        }
        return MovieEntity(
            page = movie.page ?: IF_INT_NULL,
            results = list,
            totalPages = movie.totalPages ?: IF_INT_NULL,
            totalResults = movie.totalResults ?: IF_INT_NULL
        )
    }

    private fun convertToResultMovieEntity(resultMovie: ResultMovie): ResultMovieEntity {
        val genreList = arrayListOf<Int>()
        resultMovie.genreIds?.forEach {
            genreList.add(it ?: IF_INT_NULL)
        }
        return ResultMovieEntity(
            adult = resultMovie.adult ?: IF_BOOLEAN_NULL,
            backdropPath = resultMovie.backdropPath ?: IF_STR_NULL,
            genreIds = genreList,
            id = resultMovie.id ?: IF_INT_NULL,
            originalLanguage = resultMovie.originalLanguage ?: IF_STR_NULL,
            originalTitle = resultMovie.originalTitle ?: IF_STR_NULL,
            overview = resultMovie.overview ?: IF_STR_NULL,
            popularity = resultMovie.popularity ?: IF_STR_NULL,
            posterPath = resultMovie.posterPath ?: IF_STR_NULL,
            releaseDate = resultMovie.releaseDate ?: IF_STR_NULL,
            title = resultMovie.title ?: IF_STR_NULL,
            video = resultMovie.video ?: IF_BOOLEAN_NULL,
            voteAverage = resultMovie.voteAverage ?: IF_DBL_NULL,
            voteCount = resultMovie.voteCount ?: IF_INT_NULL
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
        val castList = arrayListOf<MovieCastEntity>()
        movieCredits.cast?.let {
            it.forEach {  cast ->
                castList.add(convertToMovieCastEntity(cast))
            }
        }
        return MovieCreditsEntity(
            id = movieCredits.id ?: IF_INT_NULL,
            cast = castList
        )
    }

    private fun convertToMovieCastEntity(movieCast: MovieCast): MovieCastEntity {
        return MovieCastEntity(
            adult = movieCast.adult ?: IF_BOOLEAN_NULL,
            gender = movieCast.gender ?: IF_INT_NULL,
            id = movieCast.id ?: IF_INT_NULL,
            knownForDepartment = movieCast.knownForDepartment ?: IF_STR_NULL,
            name = movieCast.name ?: IF_STR_NULL,
            originalName = movieCast.originalName ?: IF_STR_NULL,
            popularity = movieCast.popularity ?: IF_DBL_NULL,
            profilePath = movieCast.profilePath ?: IF_STR_NULL,
            castId = movieCast.castId ?: IF_INT_NULL,
            character = movieCast.character ?: IF_STR_NULL,
            creditId = movieCast.creditId ?: IF_STR_NULL,
            order = movieCast.order ?: IF_INT_NULL


        )
    }

    fun convertToReviewListEntity(reviewList: ReviewList): ReviewListEntity {
        val reviews = arrayListOf<ReviewEntity>()
        reviewList.results?.let { review ->
            review.forEach {
                reviews.add(convertToReviewEntity(it))
            }
        }
        return ReviewListEntity(
            id = reviewList.id ?: IF_INT_NULL,
            page = reviewList.page ?: IF_INT_NULL,
            results = reviews,
            totalPages = reviewList.totalPages ?: IF_INT_NULL,
            totalResults = reviewList.totalResults ?: IF_INT_NULL
        )
    }

    private fun convertToReviewEntity(review: Review): ReviewEntity {
        return ReviewEntity(
            author = review.author ?: IF_STR_NULL,
            authorDetails = convertToAuthorDetailEntity(review.authorDetails),
            content = review.content ?: IF_STR_NULL,
            createdAt = review.createdAt ?: IF_STR_NULL,
            id = review.id ?: IF_STR_NULL,
            updatedAt = review.updatedAt ?: IF_STR_NULL,
            url = review.url ?: IF_STR_NULL
        )
    }

    private fun convertToAuthorDetailEntity(authorDetails: AuthorDetails?): AuthorDetailsEntity {
        return AuthorDetailsEntity(
            name = authorDetails?.name ?: IF_STR_NULL,
            username = authorDetails?.username ?: IF_STR_NULL,
            avatarPath = authorDetails?.avatarPath ?: IF_STR_NULL,
            rating = authorDetails?.rating ?: IF_DBL_NULL
        )
    }

    fun convertToVideoListEntity(videoList: VideoList): VideoListEntity {
        val videos = arrayListOf<VideoResultEntity>()
        videoList.results?.forEach {
            videos.add(convertToVideoResultEntity(it))
        }
        return VideoListEntity(
            id = videoList.id ?: IF_INT_NULL,
            results = videos
        )
    }

    private fun convertToVideoResultEntity(videoResult: VideoResult): VideoResultEntity {
        return VideoResultEntity(
            iso6391 = videoResult.iso6391 ?: IF_STR_NULL,
            iso31661 = videoResult.iso31661 ?: IF_STR_NULL,
            name = videoResult.name ?: IF_STR_NULL,
            key = videoResult.key ?: IF_STR_NULL,
            site = videoResult.site ?: IF_STR_NULL,
            size = videoResult.size ?: IF_INT_NULL,
            type = videoResult.type ?: IF_STR_NULL,
            official = videoResult.official ?: IF_BOOLEAN_NULL,
            publishedAt = videoResult.publishedAt ?: IF_STR_NULL,
            id = videoResult.id ?: IF_STR_NULL
        )
    }

    fun convertToMovieDetailEntity(movieDetail: MovieDetail): MovieDetailEntity {
        val productionCompanyList = arrayListOf<ProductionCountryEntity>()
        movieDetail.productionCompany?.forEach {
            productionCompanyList.add(convertToProductionCountryEntity(it))
        }
        val productionCountryList = arrayListOf<ProductionCountryEntity>()
        movieDetail.productionCountry?.forEach {
            productionCountryList.add(convertToProductionCountryEntity(it))
        }
        val languages = arrayListOf<SpokenLanguageEntity>()
        movieDetail.spokenLanguages?.forEach {
            languages.add(convertToSpokenLanguageEntity(it))
        }
        val genresList = arrayListOf<GenreEntity>()
        movieDetail.genres?.forEach {
            genresList.add(convertToGenreEntity(it))
        }
        return MovieDetailEntity(
            adult = movieDetail.adult ?: IF_BOOLEAN_NULL,
            backdropPath = movieDetail.backdropPath ?: IF_STR_NULL,
            belongsToCollection = convertBelongToCollectionEntity(movieDetail.belongsToCollection),
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
            tagline = movieDetail.tagline ?: IF_STR_NULL,
            title = movieDetail.title ?: IF_STR_NULL,
            video = movieDetail.video ?: IF_BOOLEAN_NULL,
            voteAverage = movieDetail.voteAverage ?: IF_DBL_NULL,
            voteCount = movieDetail.voteCount ?: IF_LONG_NULL
        )
    }

    private fun convertBelongToCollectionEntity(belongsToCollection: BelongsToCollection?)
            : BelongsToCollectionEntity {
        return BelongsToCollectionEntity(
            id = belongsToCollection?.id ?: IF_INT_NULL,
            name = belongsToCollection?.name ?: IF_STR_NULL,
            posterPath = belongsToCollection?.posterPath ?: IF_STR_NULL,
            backdropPath = belongsToCollection?.backdropPath ?: IF_STR_NULL
        )
    }

    private fun convertToProductionCountryEntity(productionCountry: ProductionCountry)
            : ProductionCountryEntity {
        return ProductionCountryEntity(
            id = productionCountry.id ?: IF_INT_NULL,
            logoPath = productionCountry.logoPath ?: IF_STR_NULL,
            name = productionCountry.name ?: IF_STR_NULL,
            originCountry = productionCountry.originCountry ?: IF_STR_NULL
        )

    }

    private fun convertToGenreEntity(genre: Genre): GenreEntity {
        return GenreEntity(
            id = genre.id ?: IF_INT_NULL,
            name = genre.name ?: IF_STR_NULL
        )
    }

    private fun convertToSpokenLanguageEntity(spokenLanguage: SpokenLanguage): SpokenLanguageEntity {
        return SpokenLanguageEntity(
            englishName = spokenLanguage.englishName ?: IF_STR_NULL,
            iso6391 = spokenLanguage.iso6391 ?: IF_STR_NULL,
            name = spokenLanguage.name ?: IF_STR_NULL
        )
    }
}