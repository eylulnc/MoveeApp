package com.eylulcan.moviefragment.di

import com.eylulcan.moviefragment.data.datasource.*
import com.eylulcan.moviefragment.data.datasource.remote.*
import com.eylulcan.moviefragment.data.mapper.ArtistMapper
import com.eylulcan.moviefragment.data.mapper.GenreMapper
import com.eylulcan.moviefragment.data.mapper.MovieMapper
import com.eylulcan.moviefragment.data.mapper.SearchMapper
import com.eylulcan.moviefragment.data.repository.*
import com.eylulcan.moviefragment.data.service.MovieAPI
import com.eylulcan.moviefragment.domain.repository.*
import com.google.firebase.auth.FirebaseAuth
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

}