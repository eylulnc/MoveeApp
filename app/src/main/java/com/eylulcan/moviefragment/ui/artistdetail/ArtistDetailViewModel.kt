package com.eylulcan.moviefragment.ui.artistdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eylulcan.moviefragment.api.MovieAPI
import com.eylulcan.moviefragment.model.ArtistAlbum
import com.eylulcan.moviefragment.model.ArtistDetail
import com.eylulcan.moviefragment.model.ArtistMovieCredits
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistDetailViewModel @Inject constructor(private var retrofit: MovieAPI): ViewModel() {

    private var artistDetailInfo = MutableLiveData<ArtistDetail>()
    val artistDetail: LiveData<ArtistDetail> get() = artistDetailInfo
    private var artistAlbumInfo = MutableLiveData<ArtistAlbum>()
    val artistAlbum: LiveData<ArtistAlbum> get() = artistAlbumInfo
    private var artistMovieCreditsInfo = MutableLiveData<ArtistMovieCredits>()
    val artistMovieCredits: LiveData<ArtistMovieCredits> get() = artistMovieCreditsInfo

    fun getArtistDetail(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.getArtistDetail(personId = id)
            response.let {
                if (response.isSuccessful) {
                    response.body()?.let {
                        artistDetailInfo.postValue(it)
                    }
                }
            }
        }
    }

    fun getArtistAlbum(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.getArtistImages(personId = id)
            response.let {
                if (response.isSuccessful) {
                    response.body()?.let {
                        artistAlbumInfo.postValue(it)
                    }
                }
            }
        }
    }

    fun getArtistMovieCredits(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.getArtistMovieCredits(personId = id)
            response.let {
                if (response.isSuccessful) {
                    response.body()?.let {
                        artistMovieCreditsInfo.postValue(it)
                    }
                }
            }
        }
    }

    fun setListsToDefault(){
        artistDetailInfo = MutableLiveData<ArtistDetail>()
        artistAlbumInfo = MutableLiveData<ArtistAlbum>()
        artistMovieCreditsInfo = MutableLiveData<ArtistMovieCredits>()
    }

}