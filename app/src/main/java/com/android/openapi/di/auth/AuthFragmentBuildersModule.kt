package com.android.openapi.di.auth

import com.android.openapi.ui.auth.ForgotPasswordFragment
import com.android.openapi.ui.auth.LauncherFragment
import com.android.openapi.ui.auth.LoginFragment
import com.android.openapi.ui.auth.RegisterFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class AuthFragmentBuildersModule {

    @ContributesAndroidInjector()
    abstract fun contributeLauncherFragment(): LauncherFragment

    @ContributesAndroidInjector()
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector()
    abstract fun contributeRegisterFragment(): RegisterFragment

    @ContributesAndroidInjector()
    abstract fun contributeForgotPasswordFragment(): ForgotPasswordFragment

}