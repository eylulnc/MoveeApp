package com.eylulcan.moviefragment.data.datasource

import com.eylulcan.moviefragment.data.datasource.remote.LastVisitedRemoteDataSource
import com.eylulcan.moviefragment.data.mapper.LatestVisitedMapper
import com.eylulcan.moviefragment.domain.daoEntity.MovieDao
import com.eylulcan.moviefragment.domain.daoEntity.MovieDaoEntity
import com.eylulcan.moviefragment.domain.entity.ResultData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowViaChannel
import javax.inject.Inject

private const val LAST_VISITED = "Last Visited Movies"

class LastVisitedDataSource @Inject constructor(
    private val auth: FirebaseAuth,
    private val fireStore: FirebaseFirestore,
    private val mapper: LatestVisitedMapper
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

    override suspend fun readFromDB(): Flow<ResultData<ArrayList<MovieDaoEntity>>> {
        return flowViaChannel { flowVia ->
            val movieList = arrayListOf<MovieDaoEntity>()
            val docRef = auth.currentUser?.uid?.let {
                fireStore.collection(LAST_VISITED)
                    .document(it)
            }
            docRef?.get()?.addOnSuccessListener { doc ->
                val data = doc.data
                data?.forEach { map ->
                    val movieMap = map.value as HashMap<String, *>
                    val movie:MovieDaoEntity = mapper.convertToMovieDaoEntity(movieMap)
                    movieList.add(movie)
                }
                flowVia.sendBlocking(ResultData.Success(movieList))
            }
        }
    }

}
