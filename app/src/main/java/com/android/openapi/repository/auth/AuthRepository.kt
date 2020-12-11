package com.android.openapi.repository.auth

import androidx.lifecycle.LiveData
import com.android.openapi.api.auth.OpenApiAuthService
import com.android.openapi.api.auth.network_responses.LoginResponse
import com.android.openapi.api.auth.network_responses.RegistrationResponse
import com.android.openapi.persistence.AccountPropertiesDao
import com.android.openapi.persistence.AuthTokenDao
import com.android.openapi.session.SessionManager
import com.android.openapi.util.GenericApiResponse
import javax.inject.Inject

class AuthRepository

@Inject
constructor(
    val authTokenDao: AuthTokenDao,
    val accountPropertiesDao :AccountPropertiesDao,
    val openApiAuthService: OpenApiAuthService,
    val sessionManager: SessionManager
){
    fun testLoginRequest(email :String,password : String): LiveData<GenericApiResponse<LoginResponse>>{
        return openApiAuthService.login(email,password)
    }
    fun testRegistrationRequest(
        email :String,
        username : String,
        password : String,
        confirmPassword : String
    ):LiveData<GenericApiResponse<RegistrationResponse>>{
        return openApiAuthService.register(email,username,password,confirmPassword)
    }
}