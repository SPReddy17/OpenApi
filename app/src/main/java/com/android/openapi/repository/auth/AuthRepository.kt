package com.android.openapi.repository.auth

import com.android.openapi.api.auth.OpenApiAuthService
import com.android.openapi.persistence.AccountPropertiesDao
import com.android.openapi.persistence.AuthTokenDao
import com.android.openapi.session.SessionManager
import javax.inject.Inject

class AuthRepository

@Inject
constructor(
    val authTokenDao: AuthTokenDao,
    val accountPropertiesDao :AccountPropertiesDao,
    val openApiAuthService: OpenApiAuthService,
    val sessionManager: SessionManager
)