package com.eylulcan.moviefragment.ui.genres

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eylulcan.moviefragment.api.MovieAPI
import com.eylulcan.moviefragment.model.GenreList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenresViewModel @Inject constructor(private var retrofit: MovieAPI): ViewModel() {

    private val genreList = MutableLiveData<GenreList>()
    val genres: LiveData<GenreList> get() = genreList

    fun getGenreList() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.getGenresData()
            response.let {
                if (response.isSuccessful) {
                    response.body()?.let {
                        genreList.postValue(it)
                    }
                }
            }
        }
    }
}