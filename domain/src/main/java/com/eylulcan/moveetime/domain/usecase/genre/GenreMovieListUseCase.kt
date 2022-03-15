package com.eylulcan.moveetime.domain.usecase.genre

import com.eylulcan.moveetime.domain.entity.MovieEntity
import com.eylulcan.moveetime.domain.repository.MovieRepository
import javax.inject.Inject

class GenreMovieListUseCase @Inject constructor(private val repository: MovieRepository) {
    suspend operator fun invoke(genreId: Int, pageNo: Int): MovieEntity? {
        return repository.getGenreMovieList(genreId, pageNo)
    }
}