package com.eylulcan.moviefragment.domain.usecase.lastvisited

import com.eylulcan.moviefragment.domain.daoEntity.MovieDao
import com.eylulcan.moviefragment.domain.util.ResultData
import com.eylulcan.moviefragment.domain.repository.LastVisitedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateFirestoreUseCase @Inject constructor(private val repository: LastVisitedRepository) {
    suspend operator fun invoke(movieMap: HashMap<String, MovieDao>): Flow<ResultData<Unit>> {
        return repository.updateDB(movieMap)
    }
}