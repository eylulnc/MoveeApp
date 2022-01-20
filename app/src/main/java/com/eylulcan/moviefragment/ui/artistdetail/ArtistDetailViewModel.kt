package com.eylulcan.moviefragment.ui.artistdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eylulcan.moviefragment.api.MovieAPI
import com.eylulcan.moviefragment.model.ArtistAlbum
import com.eylulcan.moviefragment.model.ArtistDetail
import com.eylulcan.moviefragment.model.ArtistMovieCredits
import com.eylulcan.moviefragment.util.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class ArtistDetailViewModel @Inject constructor(): ViewModel() {

    private var retrofit: MovieAPI? = null
    private var artistDetailInfo = MutableLiveData<ArtistDetail>()
    val artistDetail: LiveData<ArtistDetail> get() = artistDetailInfo
    private var artistAlbumInfo = MutableLiveData<ArtistAlbum>()
    val artistAlbum: LiveData<ArtistAlbum> get() = artistAlbumInfo
    private var artistMovieCreditsInfo = MutableLiveData<ArtistMovieCredits>()
    val artistMovieCredits: LiveData<ArtistMovieCredits> get() = artistMovieCreditsInfo

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(Utils.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieAPI::class.java)
    }

    fun getArtistDetail(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit?.getArtistDetail(personId = id)
            response?.let {
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
            val response = retrofit?.getArtistImages(personId = id)
            response?.let {
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
            val response = retrofit?.getArtistMovieCredits(personId = id)
            response?.let {
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