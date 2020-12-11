package com.android.openapi.ui.auth

import androidx.lifecycle.ViewModel
import com.android.openapi.repository.auth.AuthRepository
import javax.inject.Inject

class AuthViewModel
@Inject
constructor(
    val authRepository: AuthRepository
):ViewModel(){

}