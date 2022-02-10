package com.eylulcan.moviefragment.ui.lastvisited

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eylulcan.moviefragment.domain.daoEntity.MovieDao
import com.eylulcan.moviefragment.domain.daoEntity.MovieDaoEntity
import com.eylulcan.moviefragment.domain.entity.ResultData
import com.eylulcan.moviefragment.domain.usecase.lastvisited.ReadFromFirestoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LastVisitedViewModel @Inject constructor(private val readFromFirestoreUseCase: ReadFromFirestoreUseCase) :
    ViewModel() {

    private val lastVisitedRead = MutableLiveData<ResultData<ArrayList<MovieDaoEntity>>>()
    val readData: LiveData<ResultData<ArrayList<MovieDaoEntity>>> get() = lastVisitedRead

    fun readFromDB() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = readFromFirestoreUseCase.invoke()
            response.collect {
                lastVisitedRead.postValue(it)
            }
        }
    }
}
