package com.eylulcan.moviefragment.ui.genres

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eylulcan.moviefragment.domain.entity.GenreListEntity
import com.eylulcan.moviefragment.domain.usecase.genre.GenreListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenresViewModel @Inject constructor(private val genreListUseCase: GenreListUseCase) :
    ViewModel() {

    private val genreList = MutableLiveData<GenreListEntity>()
    val genres: LiveData<GenreListEntity> get() = genreList

    fun getGenreList() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = genreListUseCase.invoke()
            response.let {
                genreList.postValue(it)
            }
        }
    }
}