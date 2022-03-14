package com.eylulcan.moviefragment.data.repository

import com.eylulcan.moviefragment.data.datasource.remote.LastVisitedRemoteDataSource
import com.eylulcan.moviefragment.domain.daoEntity.MovieDao
import com.eylulcan.moviefragment.domain.daoEntity.MovieDaoEntity
import com.eylulcan.moviefragment.domain.util.ResultData
import com.eylulcan.moviefragment.domain.repository.LastVisitedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LastVisitedRepositoryImpl @Inject constructor(private val lastVisitedRemoteDataSource: LastVisitedRemoteDataSource) :
    LastVisitedRepository {
    override suspend fun updateDB(movieMap: HashMap<String, MovieDao>): Flow<ResultData<Unit>> = flow {
        emit(ResultData.Loading())
        val result = lastVisitedRemoteDataSource.updateDB(movieMap)
        result.collect {
            emit(it)
        }
    }

    override suspend fun readFromDB(): Flow<ResultData<ArrayList<MovieDaoEntity>>> = flow {
        emit(ResultData.Loading())
        val result = lastVisitedRemoteDataSource.readFromDB()
        result.collect {
            emit(it)
        }
    }

}