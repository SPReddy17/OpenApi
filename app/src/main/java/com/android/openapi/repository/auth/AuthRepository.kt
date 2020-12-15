package com.android.openapi.repository.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import com.android.openapi.api.auth.OpenApiAuthService
import com.android.openapi.api.auth.network_responses.LoginResponse
import com.android.openapi.api.auth.network_responses.RegistrationResponse
import com.android.openapi.models.AuthToken
import com.android.openapi.persistence.AccountPropertiesDao
import com.android.openapi.persistence.AuthTokenDao
import com.android.openapi.session.SessionManager
import com.android.openapi.ui.Data
import com.android.openapi.ui.DataState
import com.android.openapi.ui.Response
import com.android.openapi.ui.ResponseType
import com.android.openapi.ui.auth.state.AuthViewState
import com.android.openapi.util.ErrorHandling.Companion.ERROR_UNKNOWN
import com.android.openapi.util.GenericApiResponse
import com.android.openapi.util.GenericApiResponse.*
import javax.inject.Inject

class AuthRepository

@Inject
constructor(
    val authTokenDao: AuthTokenDao,
    val accountPropertiesDao :AccountPropertiesDao,
    val openApiAuthService: OpenApiAuthService,
    val sessionManager: SessionManager
){
   fun attemptLogin(email : String,password : String) : LiveData<DataState<AuthViewState>>{
       return openApiAuthService.login(email,password)
           .switchMap { response ->
               object : LiveData<DataState<AuthViewState>>(){
                   override fun onActive() {
                       super.onActive()
                       when(response){
                           is ApiSuccessResponse ->{
                               value = DataState.data(
                                   data = AuthViewState(
                                       authToken =  AuthToken(
                                           response.body.pk,
                                           response.body.token
                                       )
                                   ),
                                   response = null
                               )
                           }
                           is ApiErrorResponse -> {
                               value = DataState.error(
                                   response = Response(
                                       message = response.errorMessage,
                                       responseType = ResponseType.Dialog()
                                   )
                               )
                           }
                           is ApiEmptyResponse ->{
                               value = DataState.error(
                                   response = Response(
                                       message = ERROR_UNKNOWN,
                                       responseType = ResponseType.Dialog()
                                   )
                               )
                           }
                       }
                   }
               }
           }
   }

    fun attemptRegistration(
        email : String,
        username : String,
        password : String,
        confirmPassword : String
    ) : LiveData<DataState<AuthViewState>>{
        return openApiAuthService.register(email,username,password,confirmPassword)
            .switchMap { response ->
                object : LiveData<DataState<AuthViewState>>(){
                    override fun onActive() {
                        super.onActive()
                        when(response){
                            is ApiSuccessResponse ->{
                                value = DataState.data(
                                    data = AuthViewState(
                                        authToken =  AuthToken(
                                            response.body.pk,
                                            response.body.token
                                        )
                                    ),
                                    response = null
                                )
                            }
                            is ApiErrorResponse -> {
                                value = DataState.error(
                                    response = Response(
                                        message = response.errorMessage,
                                        responseType = ResponseType.Dialog()
                                    )
                                )
                            }
                            is ApiEmptyResponse ->{
                                value = DataState.error(
                                    response = Response(
                                        message = ERROR_UNKNOWN,
                                        responseType = ResponseType.Dialog()
                                    )
                                )
                            }
                        }
                    }
                }
            }
    }
}