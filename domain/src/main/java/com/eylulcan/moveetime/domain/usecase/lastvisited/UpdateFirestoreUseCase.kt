package com.eylulcan.moveetime.domain.usecase.lastvisited

import com.eylulcan.moveetime.domain.daoEntity.MovieDao
import com.eylulcan.moveetime.domain.util.ResultData
import com.eylulcan.moveetime.domain.repository.LastVisitedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateFirestoreUseCase @Inject constructor(private val repository: LastVisitedRepository) {
    suspend operator fun invoke(movieMap: HashMap<String, MovieDao>): Flow<ResultData<Unit>> {
        return repository.updateDB(movieMap)
    }
}