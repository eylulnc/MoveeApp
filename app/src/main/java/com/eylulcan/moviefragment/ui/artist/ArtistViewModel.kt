package com.eylulcan.moviefragment.ui.artist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eylulcan.moviefragment.domain.entity.ArtistListEntity
import com.eylulcan.moviefragment.domain.usecase.artist.PopularPeopleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(private val popularPeopleUseCase: PopularPeopleUseCase) :
    ViewModel() {

    private val popularPeopleList = MutableLiveData<ArtistListEntity>()
    val artistEntity: LiveData<ArtistListEntity> get() = popularPeopleList
    var lastLoadedPage: Int = 1

    fun getPopularPeople(pageNo: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = popularPeopleUseCase.invoke(pageNo)
            response.let {
                popularPeopleList.postValue(it)
            }
        }
    }
}