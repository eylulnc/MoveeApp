package com.eylulcan.moviefragment.domain.usecase.auth

import com.eylulcan.moviefragment.domain.util.ResultData
import com.eylulcan.moviefragment.domain.repository.AuthRepository
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GoogleSignInUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(credential: AuthCredential ): Flow<ResultData<Unit>> {
        return authRepository.googleSignIn(credential)

    }
}