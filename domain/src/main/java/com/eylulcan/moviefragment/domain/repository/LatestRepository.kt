package com.eylulcan.moviefragment.domain.repository

import com.eylulcan.moviefragment.domain.daoEntity.MovieDao
import com.eylulcan.moviefragment.domain.entity.ResultData
import kotlinx.coroutines.flow.Flow

interface LatestRepository {
    suspend fun updateDB(movieMap: HashMap<String, MovieDao>): Flow<ResultData<Unit>>
    suspend fun readFromDB(): Flow<ResultData<ArrayList<MovieDao>>>
}