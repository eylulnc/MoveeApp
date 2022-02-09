package com.eylulcan.moviefragment.data.datasource

import com.eylulcan.moviefragment.data.datasource.remote.LatestRemoteDataSource
import javax.inject.Inject

class LatestDataSource @Inject constructor(): LatestRemoteDataSource {
    override fun updateDB() {
        TODO("Not yet implemented")
    }

    override fun readFromDB() {
        TODO("Not yet implemented")
    }
}