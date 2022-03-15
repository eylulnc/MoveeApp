package com.eylulcan.moveetime.data.datasource

import com.eylulcan.moveetime.data.datasource.remote.LastVisitedRemoteDataSource
import com.eylulcan.moveetime.data.mapper.LatestVisitedMapper
import com.eylulcan.moveetime.domain.daoEntity.MovieDao
import com.eylulcan.moveetime.domain.daoEntity.MovieDaoEntity
import com.eylulcan.moveetime.domain.util.ResultData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

private const val LAST_VISITED = "Last Visited Movies"

class LastVisitedDataSource @Inject constructor(
    private val auth: FirebaseAuth,
    private val fireStore: FirebaseFirestore,
    private val mapper: LatestVisitedMapper
) : LastVisitedRemoteDataSource {

    override suspend fun updateDB(movieMap: HashMap<String, MovieDao>): Flow<ResultData<Unit>> {
        return callbackFlow {
            val ref = auth.currentUser?.uid?.let {
                fireStore.collection(LAST_VISITED).document(it)
            }
            ref?.set(movieMap, SetOptions.merge())?.addOnSuccessListener {
                trySend(ResultData.Success())
            }?.addOnFailureListener {
                trySend(ResultData.Failed())
            }
            awaitClose { cancel() }
        }
    }

    override suspend fun readFromDB(): Flow<ResultData<ArrayList<MovieDaoEntity>>> {
        return callbackFlow {
            val movieList = arrayListOf<MovieDaoEntity>()
            val docRef = auth.currentUser?.uid?.let {
                fireStore.collection(LAST_VISITED)
                    .document(it)
            }
            docRef?.get()?.addOnSuccessListener { doc ->
                val data = doc.data
                data?.forEach { map ->
                    val movieMap = map.value as HashMap<String, *>
                    val movie: MovieDaoEntity = mapper.convertToMovieDaoEntity(movieMap)
                    movieList.add(movie)
                }
                trySend(ResultData.Success(movieList))
            }?.addOnFailureListener {
                    trySend(ResultData.Failed())
                }
            awaitClose { cancel() }
        }
    }

}
