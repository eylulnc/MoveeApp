package com.eylulcan.moviefragment.data.datasource.remote

import com.eylulcan.moviefragment.domain.entity.ResultData
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.flow.Flow

interface AuthRemoteDataSource {
    suspend fun signUp(email: String, password: String): Flow<ResultData<Unit>>
    suspend fun signIn(email: String, password: String): Flow<ResultData<Unit>>
    suspend fun googleSignIn(credential: AuthCredential): Flow<ResultData<Unit>>
    suspend fun signOut()
}