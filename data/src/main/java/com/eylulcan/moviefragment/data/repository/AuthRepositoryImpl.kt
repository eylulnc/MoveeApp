package com.eylulcan.moviefragment.data.repository

import com.eylulcan.moviefragment.data.datasource.remote.AuthRemoteDataSource
import com.eylulcan.moviefragment.domain.util.ResultData
import com.eylulcan.moviefragment.domain.repository.AuthRepository
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val authRemoteDataSource: AuthRemoteDataSource) :
    AuthRepository {
    override suspend fun signUp(email: String, password: String): Flow<ResultData<Unit>> = flow {
        emit(ResultData.Loading())
        val result = authRemoteDataSource.signUp(email, password)
        result.collect {
            emit(it)
        }
    }

    override suspend fun signIn(email: String, password: String): Flow<ResultData<Unit>> = flow {
        emit(ResultData.Loading())
        val result = authRemoteDataSource.signIn(email, password)
        result.collect {
            emit(it)
        }
    }

    override suspend fun googleSignIn(credential: AuthCredential): Flow<ResultData<Unit>> = flow {
        emit(ResultData.Loading())
        val result = authRemoteDataSource.googleSignIn(credential)
        result.collect {
            emit(it)
        }
    }

    override suspend fun signOut(): Flow<ResultData<Unit>> = flow {
        emit(ResultData.Loading())
        val result = authRemoteDataSource.signOut()
        result.collect {
            emit(it)
        }
    }


}