package com.eylulcan.moviefragment.domain.repository

import com.eylulcan.moviefragment.domain.entity.ArtistListEntity

interface ArtistRepository {
    suspend fun getPopularPeople(pageNo:Int): ArtistListEntity?
}