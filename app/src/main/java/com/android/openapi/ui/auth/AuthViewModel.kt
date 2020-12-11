package com.android.openapi.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.openapi.api.auth.network_responses.LoginResponse
import com.android.openapi.api.auth.network_responses.RegistrationResponse
import com.android.openapi.repository.auth.AuthRepository
import com.android.openapi.util.GenericApiResponse
import javax.inject.Inject

class AuthViewModel
@Inject
constructor(
    val authRepository: AuthRepository
):ViewModel(){


    fun testLogin() : LiveData<GenericApiResponse<LoginResponse>>{
        return authRepository.testLoginRequest(
            "saiprasadreddy081@gmail.com",
            "Chinna$17"
        )
    }
    fun testRegistration() : LiveData<GenericApiResponse<RegistrationResponse>>{
        return authRepository.testRegistrationRequest(
            "saiprasadreddy081@gmail.com",
            "sp",
        "Chinna",
        "Chinna"
        )
    }
}