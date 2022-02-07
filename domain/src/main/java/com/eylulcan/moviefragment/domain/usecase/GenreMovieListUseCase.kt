package com.eylulcan.moviefragment.domain.usecase

import com.eylulcan.moviefragment.domain.entity.MovieEntity
import com.eylulcan.moviefragment.domain.repository.MovieRepository
import javax.inject.Inject

class GenreMovieListUseCase @Inject constructor(private val repository: MovieRepository) {
    suspend operator fun invoke(genreId: Int, pageNo: Int): MovieEntity? {
        return repository.getGenreMovieList(genreId, pageNo)
    }
}