package com.eylulcan.moviefragment.data.datasource

import android.util.Log
import com.eylulcan.moviefragment.data.datasource.remote.AuthRemoteDataSource
import com.eylulcan.moviefragment.domain.entity.ResultData
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

const val SUCCESS_MESSAGE = "signInWithCredential:success"
const val FAIL_MESSAGE = "signInWithCredential:failure"
private const val TAG: String = "SignInGoogle"

class AuthDataSource @Inject constructor(private val auth: FirebaseAuth) : AuthRemoteDataSource {
    override suspend fun signUp(email: String, password: String): Flow<ResultData<Unit>> {
        return callbackFlow {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    trySend(ResultData.Success())
                }
            awaitClose { cancel() }
        }
    }

    override suspend fun signIn(email: String, password: String): Flow<ResultData<Unit>> {
        return callbackFlow {
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { trySend(ResultData.Success()) }
            awaitClose { cancel() }
        }
    }

    override suspend fun googleSignIn(credential: AuthCredential): Flow<ResultData<Unit>> {
        return callbackFlow {
            auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, SUCCESS_MESSAGE)
                    } else {
                        Log.w(TAG, FAIL_MESSAGE, task.exception)
                    }
                }.addOnSuccessListener { trySend(ResultData.Success()) }
            awaitClose { cancel() }
        }

    }

    override suspend fun signOut(): Flow<ResultData<Unit>> {
        return callbackFlow {
            auth.signOut()
            if (auth.currentUser == null) {
                trySend(ResultData.Success())
            }
            awaitClose { cancel() }
        }
    }
}