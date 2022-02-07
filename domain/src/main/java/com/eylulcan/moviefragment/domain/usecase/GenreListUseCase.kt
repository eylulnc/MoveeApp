package com.eylulcan.moviefragment.domain.usecase

import com.eylulcan.moviefragment.domain.entity.GenreListEntity
import com.eylulcan.moviefragment.domain.repository.GenreRepository
import javax.inject.Inject

class GenreListUseCase @Inject constructor(private val repository: GenreRepository) {
    suspend operator fun invoke(): GenreListEntity? {
        return repository.getGenres()
    }
}