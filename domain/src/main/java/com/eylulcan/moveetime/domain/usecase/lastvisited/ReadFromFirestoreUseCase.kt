package com.eylulcan.moveetime.domain.usecase.lastvisited

import com.eylulcan.moveetime.domain.daoEntity.MovieDaoEntity
import com.eylulcan.moveetime.domain.util.ResultData
import com.eylulcan.moveetime.domain.repository.LastVisitedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadFromFirestoreUseCase @Inject constructor(private val repository: LastVisitedRepository) {
    suspend operator fun invoke(): Flow<ResultData<ArrayList<MovieDaoEntity>>> {
        return repository.readFromDB()
    }
}