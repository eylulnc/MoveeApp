package com.eylulcan.moviefragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieListViewModel : ViewModel() {

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"
    }

    private var retrofit: MovieAPI? = null
    private val popularMovieList = MutableLiveData<Movie>()
    val popularMovies: LiveData<Movie> get() = popularMovieList
    private val topRatedMovieList = MutableLiveData<Movie>()
    val topRatedMovies: LiveData<Movie> get() = topRatedMovieList
    private val genreList = MutableLiveData<GenreList>()
    val genres: LiveData<GenreList> get() = genreList
    private val popularPeopleList = MutableLiveData<PopularPeopleList>()
    val popularPeople: LiveData<PopularPeopleList> get() = popularPeopleList

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieAPI::class.java)
    }

    fun getPopularMovieList() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit?.getPopularData()

            withContext(Dispatchers.Main) {
                response?.let {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            popularMovieList.postValue(it)
                        }
                    }
                }
            }
        }
    }

    fun getTopRatedMovieList() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit?.getTopRatedData()

            withContext(Dispatchers.Main) {
                response?.let {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            topRatedMovieList.postValue(it)
                        }
                    }
                }
            }
        }
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

    fun getPopularPeople() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit?.getPopularPeople()
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