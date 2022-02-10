package com.eylulcan.moviefragment.data.repository

import com.eylulcan.moviefragment.data.datasource.remote.LatestRemoteDataSource
import com.eylulcan.moviefragment.domain.daoEntity.MovieDao
import com.eylulcan.moviefragment.domain.entity.ResultData
import com.eylulcan.moviefragment.domain.repository.LatestRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LatestRepositoryImpl @Inject constructor(private val latestRemoteDataSource: LatestRemoteDataSource) :
    LatestRepository {
    override suspend fun updateDB(movieMap: HashMap<String, MovieDao>): Flow<ResultData<Unit>> = flow {
        emit(ResultData.Loading())
        val result = latestRemoteDataSource.updateDB(movieMap)
        result.collect {
            emit(it)
        }
    }

    override suspend fun readFromDB(): Flow<ResultData<ArrayList<MovieDao>>> = flow {
        emit(ResultData.Loading())
        val result = latestRemoteDataSource.readFromDB()
        result.collect {
            emit(it)
        }
    }

}