package com.android.openapi.di.auth

import android.content.SharedPreferences
import com.android.openapi.api.auth.OpenApiAuthService
import com.android.openapi.persistence.AccountPropertiesDao
import com.android.openapi.persistence.AuthTokenDao
import com.android.openapi.repository.auth.AuthRepository
import com.android.openapi.session.SessionManager
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class AuthModule{


    @AuthScope
    @Provides
    fun provideFakeApiService(retrofitBuilder : Retrofit.Builder): OpenApiAuthService {
        return retrofitBuilder
            .build()
            .create(OpenApiAuthService::class.java)
    }

    @AuthScope
    @Provides
    fun provideAuthRepository(
        sessionManager: SessionManager,
        authTokenDao: AuthTokenDao,
        accountPropertiesDao: AccountPropertiesDao,
        openApiAuthService: OpenApiAuthService,
        sharedPreferences: SharedPreferences,
        editor: SharedPreferences.Editor
    ): AuthRepository {
        return AuthRepository(
            authTokenDao,
            accountPropertiesDao,
            openApiAuthService,
            sessionManager,
            sharedPreferences,
            editor
        )
    }

}