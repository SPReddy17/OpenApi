package com.android.openapi.ui.auth

import androidx.lifecycle.ViewModel
import com.android.openapi.repository.auth.AuthRepository

class AuthViewModel
constructor(
    val authRepository: AuthRepository
):ViewModel(){

}