package com.eylulcan.moviefragment.ui.artist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eylulcan.moviefragment.api.MovieAPI
import com.eylulcan.moviefragment.model.PopularPeopleList
import com.eylulcan.moviefragment.util.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ArtistViewModel : ViewModel() {

    private var retrofit: MovieAPI? = null
    private val popularPeopleList = MutableLiveData<PopularPeopleList>()
    val popularPeople: LiveData<PopularPeopleList> get() = popularPeopleList

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(Utils.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieAPI::class.java)
    }

    fun getPopularPeople(pageNo: Int = 1) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit?.getPopularPeople(pageNo = pageNo)
            withContext(Dispatchers.Main) {
                response?.let {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            popularPeopleList.postValue(it)
                        }
                    }
                }
            }
        }
    }
}