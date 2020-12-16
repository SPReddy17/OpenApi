package com.android.openapi.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.android.openapi.R
import com.android.openapi.ui.BaseActivity
import com.android.openapi.ui.DataState
import com.android.openapi.ui.Response
import com.android.openapi.ui.ResponseType
import com.android.openapi.ui.main.MainActivity
import com.android.openapi.viewmodels.ViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_auth.*
import javax.inject.Inject

class AuthActivity : BaseActivity(),
    NavController.OnDestinationChangedListener{


    @Inject
    lateinit var providerFactory : ViewModelProviderFactory

    lateinit var viewModel : AuthViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        viewModel = ViewModelProvider(this,providerFactory).get(AuthViewModel::class.java)
        findNavController(R.id.auth_nav_host_fragment).addOnDestinationChangedListener(this)
        subscribeObservers()

    }

    fun subscribeObservers(){

        viewModel.dataState.observe(this, Observer {dataState ->
            onDataStateChange(dataState)
            dataState.data?.let {data ->
                data.data?.let {event ->
                    event.getContentIfNotHandled()?.let {
                        it.authToken?.let {
                            Log.d(TAG, "AuthActivity ,DataState : ${it} " )
                            viewModel.setAuthToken(it)
                        }
                    }
                }
            }
        })
        viewModel.viewState.observe(this, Observer {
            it.authToken?.let {
                sessionManager.login(it)
            }
        })
        sessionManager.cachedToken.observe(this, Observer {authToken ->
            Log.d(TAG, "AuthActivity :  subscribeObservers : AuthToken: ${authToken}")
            if(authToken != null && authToken.account_pk != -1 && authToken.token != null){
                navMainActivity()
                finish()
            }
        })
    }

    private fun navMainActivity() {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        viewModel.cancelActiveJobs()
    }
    override fun displayProgressBar(bool: Boolean) {
        if(bool){
            progress_bar.visibility = View.VISIBLE
        }
        else{
            progress_bar.visibility = View.INVISIBLE
        }
    }
}