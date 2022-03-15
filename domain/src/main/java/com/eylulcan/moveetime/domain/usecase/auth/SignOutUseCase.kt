package com.eylulcan.moveetime.domain.usecase.auth

import com.eylulcan.moveetime.domain.util.ResultData
import com.eylulcan.moveetime.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignOutUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(): Flow<ResultData<Unit>> {
        return authRepository.signOut()
    }
}