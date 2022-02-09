package com.eylulcan.moviefragment.data.datasource.remote

interface LatestRemoteDataSource {
    fun updateDB()
    fun readFromDB()
}