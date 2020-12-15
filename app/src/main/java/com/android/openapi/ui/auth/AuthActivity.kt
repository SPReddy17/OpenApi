package com.android.openapi.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.openapi.R
import com.android.openapi.ui.BaseActivity
import com.android.openapi.ui.main.MainActivity
import com.android.openapi.viewmodels.ViewModelProviderFactory
import javax.inject.Inject

class AuthActivity : BaseActivity(){


    @Inject
    lateinit var providerFactory : ViewModelProviderFactory

    lateinit var viewModel : AuthViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        viewModel = ViewModelProvider(this,providerFactory).get(AuthViewModel::class.java)
        subscribeObservers()
    }

    fun subscribeObservers(){
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

}