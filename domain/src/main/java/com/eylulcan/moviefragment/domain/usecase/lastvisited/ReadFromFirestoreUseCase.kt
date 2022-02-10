package com.eylulcan.moviefragment.domain.usecase.lastvisited

import com.eylulcan.moviefragment.domain.daoEntity.MovieDao
import com.eylulcan.moviefragment.domain.entity.ResultData
import com.eylulcan.moviefragment.domain.repository.LatestRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadFromFirestoreUseCase @Inject constructor(private val repository: LatestRepository) {
    suspend operator fun invoke(): Flow<ResultData<ArrayList<MovieDao>>> {
        return repository.readFromDB()
    }
}