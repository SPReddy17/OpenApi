package com.android.openapi.repository.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import com.android.openapi.api.auth.OpenApiAuthService
import com.android.openapi.api.auth.network_responses.LoginResponse
import com.android.openapi.api.auth.network_responses.RegistrationResponse
import com.android.openapi.models.AuthToken
import com.android.openapi.persistence.AccountPropertiesDao
import com.android.openapi.persistence.AuthTokenDao
import com.android.openapi.repository.NetworkBoundResource
import com.android.openapi.session.SessionManager
import com.android.openapi.ui.Data
import com.android.openapi.ui.DataState
import com.android.openapi.ui.Response
import com.android.openapi.ui.ResponseType
import com.android.openapi.ui.auth.state.AuthViewState
import com.android.openapi.ui.auth.state.LoginFields
import com.android.openapi.ui.auth.state.RegistrationFields
import com.android.openapi.util.ErrorHandling.Companion.ERROR_UNKNOWN
import com.android.openapi.util.ErrorHandling.Companion.GENERIC_AUTH_ERROR
import com.android.openapi.util.GenericApiResponse
import com.android.openapi.util.GenericApiResponse.*
import kotlinx.coroutines.Job
import javax.inject.Inject

class AuthRepository

@Inject
constructor(
    val authTokenDao: AuthTokenDao,
    val accountPropertiesDao :AccountPropertiesDao,
    val openApiAuthService: OpenApiAuthService,
    val sessionManager: SessionManager
){

    private val TAG : String = "AppDebug"

    private var repositoryJob : Job? = null

    fun attemptLogin(email :String,password : String) : LiveData<DataState<AuthViewState>>{
        val loginFieldErrors = LoginFields(email,password).isValidForLogin()
        if(!loginFieldErrors.equals(LoginFields.LoginError.none())){
            return returnErrorResponse(loginFieldErrors,ResponseType.Dialog())
        }
        return object : NetworkBoundResource<LoginResponse, AuthViewState>(
            sessionManager.isConnectedToTheInternet()
        ){
            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<LoginResponse>) {
                Log.d(TAG, "handleApiSuccessResponse: ${response}")
                // incorrect login credentials counts as a 200 response from the server, so need to handle that
                if(response.body.response.equals(GENERIC_AUTH_ERROR)){
                    return onErrorReturn(response.body.errorMessage,true,false)
                }
                onCompleteJob(
                    DataState.data(
                        data = AuthViewState(
                            authToken = AuthToken(response.body.pk,response.body.token)
                        )
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<LoginResponse>> {
                return openApiAuthService.login(email,password)
            }

            override fun setJob(job: Job) {
                repositoryJob?.cancel()
                repositoryJob = job
            }

        }.asLiveData()
    }

    private fun returnErrorResponse(
        errorMessage: String,
        responseType: ResponseType
    ): LiveData<DataState<AuthViewState>> {
        Log.d(TAG, "returnErrorResponse: ${errorMessage}")
        return object: LiveData<DataState<AuthViewState>>(){
            override fun onActive() {
                super.onActive()
                value = DataState.error(
                    Response(
                        errorMessage,
                        responseType
                    )
                )
            }
        }

    }

    fun cancelActivejobs(){
        Log.d(TAG, "AuthRepository : Cancelling on-going jobs...")
        repositoryJob?.cancel()
    }


    fun attemptRegistration(
        email : String,
        username : String,
        password : String,
        confirmPassword : String
    ) : LiveData<DataState<AuthViewState>>{
        val registrationFieldErrors = RegistrationFields(email,username,password,confirmPassword).isValidForRegistration()
        if(!registrationFieldErrors.equals(RegistrationFields.RegistrationError.none())){
            return returnErrorResponse(registrationFieldErrors,ResponseType.Dialog( ))
        }
        return object : NetworkBoundResource<RegistrationResponse,AuthViewState>(
            sessionManager.isConnectedToTheInternet()
        ){
            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<RegistrationResponse>) {
                Log.d(TAG, "handleApiSuccessResponse: ${response}")
                if(response.body.response.equals(GENERIC_AUTH_ERROR)){
                    return onErrorReturn(response.body.errorMessage,true,false)
                }
                onCompleteJob(
                    DataState.data(
                        data = AuthViewState(
                            authToken = AuthToken(response.body.pk,response.body.token)
                        )
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<RegistrationResponse>> {
                return openApiAuthService.register(email,username,password,confirmPassword)
            }

            override fun setJob(job: Job) {
                repositoryJob?.cancel()
                repositoryJob = job
            }

        }.asLiveData()
    }


}