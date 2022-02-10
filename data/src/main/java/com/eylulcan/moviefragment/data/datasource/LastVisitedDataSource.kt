package com.eylulcan.moviefragment.data.datasource

import com.eylulcan.moviefragment.data.datasource.remote.LastVisitedRemoteDataSource
import com.eylulcan.moviefragment.domain.daoEntity.MovieDao
import com.eylulcan.moviefragment.domain.entity.ResultData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowViaChannel
import javax.inject.Inject

private const val LAST_VISITED = "Last Visited Movies"
private const val ID = "id"
private const val POSTER_PATH = "posterPath"
private const val TITLE = "title"

class LastVisitedDataSource @Inject constructor(
    private val auth: FirebaseAuth,
    private val fireStore: FirebaseFirestore
) : LastVisitedRemoteDataSource {
    override suspend fun updateDB(movieMap: HashMap<String, MovieDao>): Flow<ResultData<Unit>> {
        return flowViaChannel { flowVia ->
            val ref = auth.currentUser?.uid?.let {
                fireStore.collection(LAST_VISITED).document(it)
            }
            ref?.set(movieMap, SetOptions.merge())?.addOnSuccessListener {
                flowVia.sendBlocking(ResultData.Success())
            }
        }
    }

    override suspend fun readFromDB(): Flow<ResultData<ArrayList<MovieDao>>> {
        return flowViaChannel { flowVia ->
            var movie: MovieDao
            val movieList = arrayListOf<MovieDao>()
            val docRef = auth.currentUser?.uid?.let {
                fireStore.collection(LAST_VISITED)
                    .document(it)
            }
            docRef?.get()?.addOnSuccessListener { doc ->
                val data = doc.data
                data?.forEach { map ->
                    val movieMap = map.value as HashMap<String, *>
                    val id = movieMap[ID].toString()
                    val title = movieMap[TITLE] as String
                    val posterPath = movieMap[POSTER_PATH] as String
                    movie = MovieDao(id = id, title = title, posterPath = posterPath)
                    movieList.add(movie)
                }
                flowVia.sendBlocking(ResultData.Success(movieList))
            }
        }
    }

}
