package com.eylulcan.moveetime.data.mapper

import com.eylulcan.moveetime.data.Utils
import com.eylulcan.moveetime.domain.daoEntity.MovieDaoEntity
import javax.inject.Inject

private const val ID = "id"
private const val POSTER_PATH = "posterPath"
private const val TITLE = "title"

class LatestVisitedMapper @Inject constructor(){
    fun convertToMovieDaoEntity(movieMap: HashMap<String, *>): MovieDaoEntity {
        val id = movieMap[ID] as? String
        val title = movieMap[TITLE] as? String ?: Utils.IF_STR_NULL
        val posterPath = movieMap[POSTER_PATH] as? String ?: Utils.IF_STR_NULL
        return MovieDaoEntity(
            id = id?.toIntOrNull() ?: Utils.IF_INT_NULL,
            title = title,
            posterPath = posterPath
        )
    }
}