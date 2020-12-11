package com.android.openapi.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.android.openapi.R
import com.android.openapi.util.GenericApiResponse

class RegisterFragment : BaseAuthFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "RegisterFragment: ${viewModel.hashCode()} ")

        viewModel.testRegistration().observe(viewLifecycleOwner, Observer {response ->
            when(response){

                is GenericApiResponse.ApiSuccessResponse -> {
                    Log.d(TAG, "Registration response :  ${response.body}")
                }
                is GenericApiResponse.ApiErrorResponse -> {
                    Log.d(TAG, "Registration response :  ${response.errorMessage}")
                }
                is GenericApiResponse.ApiEmptyResponse -> {
                    Log.d(TAG, "Registration response : empty response")

                }
            }
        })
    }
}