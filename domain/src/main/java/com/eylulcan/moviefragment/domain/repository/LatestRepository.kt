package com.eylulcan.moviefragment.domain.repository

interface LatestRepository {
    fun updateDB()
    fun readFromDB()
}