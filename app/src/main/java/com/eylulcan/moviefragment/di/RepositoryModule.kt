package com.eylulcan.moviefragment.di

import com.eylulcan.moviefragment.data.datasource.*
import com.eylulcan.moviefragment.data.datasource.remote.ArtistRemoteDataSource
import com.eylulcan.moviefragment.data.datasource.remote.GenreRemoteDataSource
import com.eylulcan.moviefragment.data.datasource.remote.MovieRemoteDataSource
import com.eylulcan.moviefragment.data.datasource.remote.SearchRemoteDataSource
import com.eylulcan.moviefragment.data.mapper.ArtistMapper
import com.eylulcan.moviefragment.data.mapper.GenreMapper
import com.eylulcan.moviefragment.data.mapper.MovieMapper
import com.eylulcan.moviefragment.data.mapper.SearchMapper
import com.eylulcan.moviefragment.data.repository.ArtistRepositoryImpl
import com.eylulcan.moviefragment.data.repository.GenreRepositoryImpl
import com.eylulcan.moviefragment.data.repository.MovieRepositoryImpl
import com.eylulcan.moviefragment.data.repository.SearchRepositoryImpl
import com.eylulcan.moviefragment.data.service.MovieAPI
import com.eylulcan.moviefragment.domain.repository.ArtistRepository
import com.eylulcan.moviefragment.domain.repository.GenreRepository
import com.eylulcan.moviefragment.domain.repository.MovieRepository
import com.eylulcan.moviefragment.domain.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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

}