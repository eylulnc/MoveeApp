package com.eylulcan.moviefragment.domain.usecase

import com.eylulcan.moviefragment.domain.entity.GenreListEntity
import com.eylulcan.moviefragment.domain.repository.GenreRepository

class GenreListUseCase (private val repository: GenreRepository) {
    suspend operator fun invoke(): GenreListEntity? {
        return repository.getGenres()
    }
}