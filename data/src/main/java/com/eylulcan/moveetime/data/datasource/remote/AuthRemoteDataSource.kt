package com.eylulcan.moveetime.data.datasource.remote

import com.eylulcan.moveetime.domain.util.ResultData
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.flow.Flow

interface AuthRemoteDataSource {
    suspend fun signUp(email: String, password: String): Flow<ResultData<Unit>>
    suspend fun signIn(email: String, password: String): Flow<ResultData<Unit>>
    suspend fun googleSignIn(credential: AuthCredential): Flow<ResultData<Unit>>
    suspend fun signOut(): Flow<ResultData<Unit>>
}