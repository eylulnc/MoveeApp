package com.eylulcan.moveetime.ui.artistdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eylulcan.moveetime.domain.entity.ArtistAlbumEntity
import com.eylulcan.moveetime.domain.entity.ArtistDetailEntity
import com.eylulcan.moveetime.domain.entity.ArtistMovieCreditsEntity
import com.eylulcan.moveetime.domain.usecase.artist.ArtistAlbumUseCase
import com.eylulcan.moveetime.domain.usecase.artist.ArtistDetailUseCase
import com.eylulcan.moveetime.domain.usecase.artist.ArtistMovieCreditsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistDetailViewModel @Inject constructor(
    private val artistAlbumUseCase: ArtistAlbumUseCase,
    private val artistDetailUseCase: ArtistDetailUseCase,
    private val artistMovieCreditsUseCase: ArtistMovieCreditsUseCase
) : ViewModel() {

    private var artistDetailInfo = MutableLiveData<ArtistDetailEntity>()
    val artistDetail: LiveData<ArtistDetailEntity> get() = artistDetailInfo
    private var artistAlbumInfo = MutableLiveData<ArtistAlbumEntity>()
    val artistAlbum: LiveData<ArtistAlbumEntity> get() = artistAlbumInfo
    private var artistMovieCreditsInfo = MutableLiveData<ArtistMovieCreditsEntity>()
    val artistMovieCredits: LiveData<ArtistMovieCreditsEntity> get() = artistMovieCreditsInfo

    fun getArtistDetail(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = artistDetailUseCase.invoke(id)
            response.let {
                artistDetailInfo.postValue(it)
            }
        }
    }

    fun getArtistAlbum(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = artistAlbumUseCase.invoke(id)
            response.let {
                artistAlbumInfo.postValue(it)
            }
        }
    }

    fun getArtistMovieCredits(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = artistMovieCreditsUseCase.invoke(id)
            response.let {
                artistMovieCreditsInfo.postValue(it)
            }
        }
    }

    fun setListsToDefault() {
        artistDetailInfo = MutableLiveData<ArtistDetailEntity>()
        artistAlbumInfo = MutableLiveData<ArtistAlbumEntity>()
        artistMovieCreditsInfo = MutableLiveData<ArtistMovieCreditsEntity>()
    }

}