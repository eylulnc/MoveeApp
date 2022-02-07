package com.eylulcan.moviefragment.data.datasource.remote

import com.eylulcan.moviefragment.domain.entity.ArtistAlbumEntity
import com.eylulcan.moviefragment.domain.entity.ArtistDetailEntity
import com.eylulcan.moviefragment.domain.entity.ArtistListEntity
import com.eylulcan.moviefragment.domain.entity.ArtistMovieCreditsEntity

interface ArtistRemoteDataSource {
    suspend fun getPopularPeople(pageNo:Int): ArtistListEntity?
    suspend fun getArtistDetail(id:Int): ArtistDetailEntity?
    suspend fun getArtistAlbum(id:Int): ArtistAlbumEntity?
    suspend fun getArtistMovieCredits(id:Int): ArtistMovieCreditsEntity?
}