package com.eylulcan.moviefragment.domain.usecase

import com.eylulcan.moviefragment.domain.entity.MovieEntity
import com.eylulcan.moviefragment.domain.repository.MovieRepository

class GenreMovieListUseCase (private val repository: MovieRepository) {
    suspend operator fun invoke(genreId :Int, pageNo: Int): MovieEntity? {
        return repository.getGenreMovieList(genreId,pageNo)
    }
}