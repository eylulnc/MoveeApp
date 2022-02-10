package com.eylulcan.moviefragment.data.datasource.remote

import com.eylulcan.moviefragment.domain.daoEntity.MovieDao
import com.eylulcan.moviefragment.domain.entity.ResultData
import kotlinx.coroutines.flow.Flow

interface LastVisitedRemoteDataSource {
    suspend fun updateDB(movieMap: HashMap<String, MovieDao>): Flow<ResultData<Unit>>
    suspend fun readFromDB(): Flow<ResultData<ArrayList<MovieDao>>>
}