package com.eylulcan.moviefragment.ui.genres.movielist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eylulcan.moviefragment.api.MovieAPI
import com.eylulcan.moviefragment.model.Movie
import com.eylulcan.moviefragment.util.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class GenreMovieListViewModel @Inject constructor(): ViewModel() {

    private var retrofit: MovieAPI? = null
    private val movieList = MutableLiveData<Movie>()
    val movies: LiveData<Movie> get() = movieList
    var lastLoadedPage = 1

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(Utils.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieAPI::class.java)
        movieList.postValue(Movie())
    }

    fun getMovieListByGenre(genreId: Int, pageNo: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            if (pageNo == 1) {
                movieList.postValue(Movie())
            }
            val response = retrofit?.getMovieByGenreId(genreId = genreId, pageNo = pageNo)
            response?.let {
                if (response.isSuccessful) {
                    response.body()?.let {
                        movieList.postValue(it)
                    }
                }
            }
        }
    }
}