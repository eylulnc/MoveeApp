package com.eylulcan.moveetime.domain.usecase.auth

import com.eylulcan.moveetime.domain.util.ResultData
import com.eylulcan.moveetime.domain.repository.AuthRepository
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GoogleSignInUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(credential: AuthCredential ): Flow<ResultData<Unit>> {
        return authRepository.googleSignIn(credential)

    }
}