package com.eylulcan.moveetime.domain.usecase.auth

import com.eylulcan.moveetime.domain.util.ResultData
import com.eylulcan.moveetime.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email:String, password:String ): Flow<ResultData<Unit>> {
        return authRepository.signIn(email, password)

    }
}