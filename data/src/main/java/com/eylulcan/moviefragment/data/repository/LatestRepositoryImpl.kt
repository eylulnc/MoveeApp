package com.eylulcan.moviefragment.data.repository

import com.eylulcan.moviefragment.data.datasource.remote.LatestRemoteDataSource
import com.eylulcan.moviefragment.domain.repository.LatestRepository
import javax.inject.Inject

class LatestRepositoryImpl @Inject constructor(private val latestRemoteDataSource: LatestRemoteDataSource) : LatestRepository {
    override fun updateDB() {
        latestRemoteDataSource.updateDB()
    }

    override fun readFromDB() {
        latestRemoteDataSource.readFromDB()
    }
}