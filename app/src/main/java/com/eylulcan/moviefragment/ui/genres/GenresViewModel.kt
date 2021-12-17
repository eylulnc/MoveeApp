package com.eylulcan.moviefragment.ui.genres

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eylulcan.moviefragment.api.MovieAPI
import com.eylulcan.moviefragment.model.GenreList
import com.eylulcan.moviefragment.model.Movie
import com.eylulcan.moviefragment.util.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GenresViewModel : ViewModel() {

    private var retrofit: MovieAPI? = null
    private val genreList = MutableLiveData<GenreList>()
    val genres: LiveData<GenreList> get() = genreList
    private val movieList = MutableLiveData<Movie>()
    val movies: LiveData<Movie> get() = movieList

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(Utils.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieAPI::class.java)
    }

    fun getGenreList() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit?.getGenresData()
            withContext(Dispatchers.Main) {
                response?.let {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            genreList.postValue(it)

                        }
                    }
                }
            }
        }
    }

    fun getMovieListByGenre(genreId: Int, pageNo: Int = 1) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit?.getMovieByGenreId(genreId = genreId, pageNo = pageNo)
            withContext(Dispatchers.Main) {
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
}