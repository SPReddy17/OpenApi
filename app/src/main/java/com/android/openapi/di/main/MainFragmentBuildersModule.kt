package com.android.openapi.di.main

import com.android.openapi.ui.main.account.AccountFragment
import com.android.openapi.ui.main.account.ChangePasswordFragment
import com.android.openapi.ui.main.account.UpdateAccountFragment
import com.android.openapi.ui.main.blog.BlogFragment
import com.android.openapi.ui.main.blog.UpdateBlogFragment
import com.android.openapi.ui.main.blog.ViewBlogFragment
import com.android.openapi.ui.main.create_blog.CreateBlogFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector()
    abstract fun contributeBlogFragment(): BlogFragment

    @ContributesAndroidInjector()
    abstract fun contributeAccountFragment(): AccountFragment

    @ContributesAndroidInjector()
    abstract fun contributeChangePasswordFragment(): ChangePasswordFragment

    @ContributesAndroidInjector()
    abstract fun contributeCreateBlogFragment(): CreateBlogFragment

    @ContributesAndroidInjector()
    abstract fun contributeUpdateBlogFragment(): UpdateBlogFragment

    @ContributesAndroidInjector()
    abstract fun contributeViewBlogFragment(): ViewBlogFragment

    @ContributesAndroidInjector()
    abstract fun contributeUpdateAccountFragment(): UpdateAccountFragment
}