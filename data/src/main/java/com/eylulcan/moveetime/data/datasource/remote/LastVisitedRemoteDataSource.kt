package com.eylulcan.moveetime.data.datasource.remote

import com.eylulcan.moveetime.domain.daoEntity.MovieDao
import com.eylulcan.moveetime.domain.daoEntity.MovieDaoEntity
import com.eylulcan.moveetime.domain.util.ResultData
import kotlinx.coroutines.flow.Flow

interface LastVisitedRemoteDataSource {
    suspend fun updateDB(movieMap: HashMap<String, MovieDao>): Flow<ResultData<Unit>>
    suspend fun readFromDB(): Flow<ResultData<ArrayList<MovieDaoEntity>>>
}