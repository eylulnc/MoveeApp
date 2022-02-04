package com.eylulcan.moviefragment.domain.usecase

import com.eylulcan.moviefragment.domain.entity.ArtistListEntity
import com.eylulcan.moviefragment.domain.repository.ArtistRepository

class PopularPeopleUseCase (private val repository: ArtistRepository) {
    suspend operator fun invoke(pageNo: Int): ArtistListEntity? {
        return repository.getPopularPeople(pageNo)

    }
}