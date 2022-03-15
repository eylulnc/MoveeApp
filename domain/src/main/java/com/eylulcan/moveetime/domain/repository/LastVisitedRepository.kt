package com.eylulcan.moveetime.domain.repository

import com.eylulcan.moveetime.domain.daoEntity.MovieDao
import com.eylulcan.moveetime.domain.daoEntity.MovieDaoEntity
import com.eylulcan.moveetime.domain.util.ResultData
import kotlinx.coroutines.flow.Flow

interface LastVisitedRepository {
    suspend fun updateDB(movieMap: HashMap<String, MovieDao>): Flow<ResultData<Unit>>
    suspend fun readFromDB(): Flow<ResultData<ArrayList<MovieDaoEntity>>>
}