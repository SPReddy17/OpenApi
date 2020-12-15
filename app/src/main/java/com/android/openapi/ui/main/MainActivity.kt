package com.android.openapi.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.lifecycle.Observer
import com.android.openapi.R
import com.android.openapi.ui.BaseActivity
import com.android.openapi.ui.auth.AuthActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity :BaseActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        subscribeObservers()
    }
    
    fun subscribeObservers(){
        sessionManager.cachedToken.observe(this, Observer {authToken ->
            Log.d(TAG, "subscribeObservers:  subscribeObservers : AuthToken: ${authToken}")
            if(authToken == null || authToken.account_pk == -1 || authToken.token == null){
                navAuthActivity()
                finish()
            }
        })
    }

    private fun navAuthActivity() {
        val intent = Intent(this,AuthActivity::class.java)
        startActivity(intent)
        finish()
    }
}
