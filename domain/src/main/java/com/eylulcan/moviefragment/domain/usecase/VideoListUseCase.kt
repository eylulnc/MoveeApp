package com.eylulcan.moviefragment.domain.usecase

import com.eylulcan.moviefragment.domain.entity.VideoListEntity
import com.eylulcan.moviefragment.domain.repository.MovieRepository
import javax.inject.Inject

class VideoListUseCase @Inject constructor(private val repository: MovieRepository) {

    suspend operator fun invoke(movieID: Int): VideoListEntity? {
        return repository.getMovieVideo(movieID)

    }
}