package com.eylulcan.moviefragment.ui.artist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eylulcan.moviefragment.api.MovieAPI
import com.eylulcan.moviefragment.model.PopularPeopleList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(private var retrofit: MovieAPI): ViewModel() {

    private val popularPeopleList = MutableLiveData<PopularPeopleList>()
    val popularPeople: LiveData<PopularPeopleList> get() = popularPeopleList
    var lastLoadedPage: Int = 1

    fun getPopularPeople(pageNo:Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.getPopularPeople(pageNo = pageNo)
            response.let {
                if (response.isSuccessful) {
                    response.body()?.let {
                        popularPeopleList.postValue(it)
                    }
                }
            }
        }
    }

}