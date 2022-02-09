package com.eylulcan.moviefragment.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eylulcan.moviefragment.domain.entity.ResultData
import com.eylulcan.moviefragment.domain.usecase.auth.GoogleSignInUseCase
import com.eylulcan.moviefragment.domain.usecase.auth.SignInUseCase
import com.eylulcan.moviefragment.domain.usecase.auth.SignUpUseCase
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val googleSignInUseCase: GoogleSignInUseCase
) : ViewModel() {

    private var signInResultData = MutableLiveData<ResultData<Unit>>()
    val signInResults: LiveData<ResultData<Unit>> get() = signInResultData
    private var signUpResultData = MutableLiveData<ResultData<Unit>>()
    val signUpResults: LiveData<ResultData<Unit>> get() = signUpResultData
    private var googleSignInResultData = MutableLiveData<ResultData<Unit>>()
    val googleSignInResults: LiveData<ResultData<Unit>> get() = googleSignInResultData

    fun signIn(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = signInUseCase.invoke(email, password)
            response.collect {
                signInResultData.postValue(it)
            }
        }
    }

    fun signUp(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = signUpUseCase.invoke(email, password)
            response.collect {
                signUpResultData.postValue(it)
            }
        }
    }

    fun signInGoogle(credential: AuthCredential) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = googleSignInUseCase.invoke(credential = credential)
            response.collect {
                googleSignInResultData.postValue(it)
            }
        }
    }
}