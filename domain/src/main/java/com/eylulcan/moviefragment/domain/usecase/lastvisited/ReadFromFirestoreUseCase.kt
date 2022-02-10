package com.eylulcan.moviefragment.domain.usecase.lastvisited

import com.eylulcan.moviefragment.domain.daoEntity.MovieDaoEntity
import com.eylulcan.moviefragment.domain.entity.ResultData
import com.eylulcan.moviefragment.domain.repository.LastVisitedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadFromFirestoreUseCase @Inject constructor(private val repository: LastVisitedRepository) {
    suspend operator fun invoke(): Flow<ResultData<ArrayList<MovieDaoEntity>>> {
        return repository.readFromDB()
    }
}