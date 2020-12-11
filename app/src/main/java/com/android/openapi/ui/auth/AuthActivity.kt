package com.android.openapi.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.openapi.R
import com.android.openapi.ui.BaseActivity

class AuthActivity : BaseActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }

}