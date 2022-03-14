package com.eylulcan.moviefragment.domain.usecase.auth

import com.eylulcan.moviefragment.domain.util.ResultData
import com.eylulcan.moviefragment.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignUpUseCase  @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email:String, password:String ): Flow<ResultData<Unit>> {
        return authRepository.signUp(email, password)

    }
}