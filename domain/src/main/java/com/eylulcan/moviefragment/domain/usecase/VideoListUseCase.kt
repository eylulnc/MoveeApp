package com.eylulcan.moviefragment.domain.usecase

import com.eylulcan.moviefragment.domain.entity.VideoListEntity
import com.eylulcan.moviefragment.domain.repository.MovieRepository

class VideoListUseCase (private val repository: MovieRepository) {

    suspend operator fun invoke(movieID: Int): VideoListEntity? {
        return repository.getMovieVideo(movieID)

    }
}