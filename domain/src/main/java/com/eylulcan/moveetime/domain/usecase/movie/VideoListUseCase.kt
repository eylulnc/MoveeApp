package com.eylulcan.moveetime.domain.usecase.movie

import com.eylulcan.moveetime.domain.entity.VideoListEntity
import com.eylulcan.moveetime.domain.repository.MovieRepository
import javax.inject.Inject

class VideoListUseCase @Inject constructor(private val repository: MovieRepository) {

    suspend operator fun invoke(movieID: Int): VideoListEntity? {
        return repository.getMovieVideo(movieID)

    }
}