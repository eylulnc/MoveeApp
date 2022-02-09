package com.eylulcan.moviefragment.domain.usecase.auth

import com.eylulcan.moviefragment.domain.repository.AuthRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke() {
        return authRepository.signOut()
    }
}