package com.eylulcan.moveetime.di

import com.eylulcan.moveetime.data.datasource.*
import com.eylulcan.moveetime.data.datasource.remote.*
import com.eylulcan.moveetime.data.mapper.*
import com.eylulcan.moveetime.data.repository.*
import com.eylulcan.moveetime.data.service.MovieAPI
import com.eylulcan.moveetime.domain.repository.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun providesMovieRepository(
        dataSource: MovieRemoteDataSource
    ): MovieRepository {
        return MovieRepositoryImpl(
            dataSource
        )
    }

    @Provides
    fun providesMovieDataSource(
        movieAPI: MovieAPI,
        movieMapper: MovieMapper
    ): MovieRemoteDataSource {
        return MovieDataSource(movieAPI, movieMapper)
    }

    @Provides
    fun provideMovieMapper(): MovieMapper {
        return MovieMapper()
    }

    @Provides
    fun providesArtistRepository(
        dataSource: ArtistRemoteDataSource
    ): ArtistRepository {
        return ArtistRepositoryImpl(
            dataSource
        )
    }

    @Provides
    fun providesArtistDataSource(
        movieAPI: MovieAPI,
        artistMapper: ArtistMapper
    ): ArtistRemoteDataSource {
        return ArtistDataSource(movieAPI, artistMapper)
    }

    @Provides
    fun provideArtistMapper(): ArtistMapper {
        return ArtistMapper()
    }

    @Provides
    fun providesSearchRepository(
        dataSource: SearchRemoteDataSource
    ): SearchRepository {
        return SearchRepositoryImpl(
            dataSource
        )
    }

    @Provides
    fun providesSearchDataSource(
        movieAPI: MovieAPI,
        searchMapper: SearchMapper
    ): SearchRemoteDataSource {
        return SearchDataSource(movieAPI, searchMapper)
    }

    @Provides
    fun provideSearchMapper(): SearchMapper {
        return SearchMapper()
    }

    @Provides
    fun provideGenreRepository(
        dataSource: GenreRemoteDataSource
    ): GenreRepository {
        return GenreRepositoryImpl(
            dataSource
        )
    }

    @Provides
    fun providesGenreDataSource(
        movieAPI: MovieAPI,
        genreMapper: GenreMapper
    ): GenreRemoteDataSource {
        return GenreDataSource(movieAPI, genreMapper)
    }

    @Provides
    fun provideGenreMapper(): GenreMapper {
        return GenreMapper()
    }

    @Provides
    fun providesAuthRepository(
        dataSource: AuthRemoteDataSource
    ): AuthRepository {
        return AuthRepositoryImpl(
            dataSource
        )
    }

    @Provides
    fun providesAuthDataSource(
    ): AuthRemoteDataSource {
        return AuthDataSource(FirebaseAuth.getInstance())
    }

    @Provides
    fun providesLatestRepository(
        dataSource: LastVisitedDataSource
    ): LastVisitedRepository {
        return LastVisitedRepositoryImpl(
            dataSource
        )
    }

    @Provides
    fun providesLastVisitedDataSource(
        auth: FirebaseAuth,
        fireStore: FirebaseFirestore,
        mapper: LatestVisitedMapper
    ): LastVisitedRemoteDataSource {
        return LastVisitedDataSource(auth, fireStore, mapper)
    }

    @Provides
    fun latestVisitedMapper(): LatestVisitedMapper {
        return LatestVisitedMapper()
    }

}