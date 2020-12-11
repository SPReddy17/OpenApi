package com.android.openapi.session

import android.app.Application
import com.android.openapi.persistence.AuthTokenDao

class SessionManager
constructor(

    val authTokenDao: AuthTokenDao,
    val application: Application
)
{

}