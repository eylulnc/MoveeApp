package com.eylulcan.moviefragment.domain.usecase

import com.eylulcan.moviefragment.domain.entity.ArtistListEntity
import com.eylulcan.moviefragment.domain.repository.ArtistRepository
import javax.inject.Inject

class PopularPeopleUseCase @Inject constructor(private val repository: ArtistRepository) {
    suspend operator fun invoke(pageNo: Int): ArtistListEntity? {
        return repository.getPopularPeople(pageNo)

    }
}