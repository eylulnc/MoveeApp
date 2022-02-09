package com.eylulcan.moviefragment.domain.entity

sealed class ResultData<out T> {
    data class Loading(val nothing: Nothing? = null) : ResultData<Nothing>()
    data class Success<out T>(val data: T? = null) : ResultData<T>()
    data class Failed(val error: String? = null) : ResultData<Nothing>()
    data class Progress<out T>(val progress: T? = null) : ResultData<T>()

    fun toData(): T? = if(this is Success) this.data else null
}