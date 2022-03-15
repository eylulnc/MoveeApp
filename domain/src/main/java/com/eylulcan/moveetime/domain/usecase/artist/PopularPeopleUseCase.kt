package com.eylulcan.moveetime.domain.usecase.artist

import com.eylulcan.moveetime.domain.entity.ArtistListEntity
import com.eylulcan.moveetime.domain.repository.ArtistRepository
import javax.inject.Inject

class PopularPeopleUseCase @Inject constructor(private val repository: ArtistRepository) {
    suspend operator fun invoke(pageNo: Int): ArtistListEntity? {
        return repository.getPopularPeople(pageNo)

    }
}