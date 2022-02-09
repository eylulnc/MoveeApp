package com.eylulcan.moviefragment.domain.usecase.auth

import com.eylulcan.moviefragment.domain.entity.ResultData
import com.eylulcan.moviefragment.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email:String, password:String ): Flow<ResultData<Unit>> {
        return authRepository.signIn(email, password)

    }
}