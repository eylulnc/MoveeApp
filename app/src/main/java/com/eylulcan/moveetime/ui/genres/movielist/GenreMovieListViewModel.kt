package com.eylulcan.moveetime.ui.genres.movielist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eylulcan.moveetime.domain.entity.MovieEntity
import com.eylulcan.moveetime.domain.usecase.genre.GenreMovieListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreMovieListViewModel @Inject constructor(private val genreMovieListUseCase: GenreMovieListUseCase) :
    ViewModel() {

    private val movieList = MutableLiveData<MovieEntity>()
    val movies: LiveData<MovieEntity> get() = movieList
    var lastLoadedPage = 1

    fun getMovieListByGenre(genreId: Int, pageNo: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = genreMovieListUseCase.invoke(genreId = genreId, pageNo = pageNo)
            response.let {
                movieList.postValue(it)
            }
        }
    }
}