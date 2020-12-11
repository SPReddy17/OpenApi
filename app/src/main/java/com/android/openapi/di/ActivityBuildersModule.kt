package com.android.openapi.di

import com.android.openapi.di.auth.AuthFragmentBuildersModule
import com.android.openapi.di.auth.AuthModule
import com.android.openapi.di.auth.AuthScope
import com.android.openapi.di.auth.AuthViewModelModule
import com.android.openapi.ui.auth.AuthActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuildersModule {

    @AuthScope
    @ContributesAndroidInjector(
        modules = [AuthModule::class, AuthFragmentBuildersModule::class, AuthViewModelModule::class]
    )
    abstract fun contributeAuthActivity(): AuthActivity

}