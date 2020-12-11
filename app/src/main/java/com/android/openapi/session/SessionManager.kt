package com.android.openapi.session

import android.app.Application
import com.android.openapi.persistence.AuthTokenDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager
@Inject
constructor(

    val authTokenDao: AuthTokenDao,
    val application: Application
)
{

}