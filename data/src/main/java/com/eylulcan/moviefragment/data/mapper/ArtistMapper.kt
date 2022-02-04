package com.eylulcan.moviefragment.data.mapper

import com.eylulcan.moviefragment.data.Utils.IF_INT_NULL
import com.eylulcan.moviefragment.data.model.ArtistAlbum
import com.eylulcan.moviefragment.data.model.ArtistList
import com.eylulcan.moviefragment.domain.entity.ArtistAlbumEntity
import com.eylulcan.moviefragment.domain.entity.ArtistListEntity
import com.eylulcan.moviefragment.domain.entity.ArtistResultEntity
import javax.inject.Inject

class ArtistMapper @Inject constructor() {

    fun convertToArtistAlbumEntity(artistAlbum: ArtistAlbum): ArtistAlbumEntity {
        return ArtistAlbumEntity(
                id = artistAlbum.id ?: -1,
                artistProfileImages = artistAlbum.artistProfileImageEntities ?: emptyList()
            )
    }

    fun convertToArtistListEntity(artistList: ArtistList): ArtistListEntity {
        val artistResultList = arrayListOf<ArtistResultEntity>()
        artistList.results?.forEach {
            artistResultList.add(it)
        }
        return ArtistListEntity(
            page = artistList.page ?: IF_INT_NULL,
            results = artistResultList,
            totalPages = artistList.totalPages ?: IF_INT_NULL,
            totalResults = artistList.totalResults ?: IF_INT_NULL
        )
    }



}
