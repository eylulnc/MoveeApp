package com.eylulcan.moveetime.domain.usecase.genre

import com.eylulcan.moveetime.domain.entity.GenreListEntity
import com.eylulcan.moveetime.domain.repository.GenreRepository
import javax.inject.Inject

class GenreListUseCase @Inject constructor(private val repository: GenreRepository) {
    suspend operator fun invoke(): GenreListEntity? {
        return repository.getGenres()
    }
}