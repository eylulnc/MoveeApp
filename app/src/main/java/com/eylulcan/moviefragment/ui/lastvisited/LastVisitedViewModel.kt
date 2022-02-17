package com.eylulcan.moviefragment.ui.lastvisited

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eylulcan.moviefragment.domain.daoEntity.MovieDaoEntity
import com.eylulcan.moviefragment.domain.entity.ResultData
import com.eylulcan.moviefragment.domain.usecase.lastvisited.ReadFromFirestoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LastVisitedViewModel @Inject constructor(private val readFromFirestoreUseCase: ReadFromFirestoreUseCase) :
    ViewModel() {

    var movieList = mutableStateOf<ArrayList<MovieDaoEntity>>(arrayListOf())
    var isLoading = mutableStateOf(false)

    fun readFromDB() {
        viewModelScope.launch {
            isLoading.value = true
            val response = readFromFirestoreUseCase.invoke()
            response.collect {
                when (it) {
                    is ResultData.Success -> {
                        val movieItems = it.data!!.mapIndexed { index, movie ->
                            MovieDaoEntity(movie.id, movie.title, movie.posterPath)
                        } as ArrayList<MovieDaoEntity>

                        movieList.value += movieItems
                        isLoading.value = false
                    }
                    else -> {}
                }
            }
        }
    }
}
