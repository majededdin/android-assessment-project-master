package com.vp.favorites.di

import com.vp.favorites.ui.activities.FavoriteActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FavoriteActivityModule {
    @ContributesAndroidInjector(modules = [FavoriteViewModelModule::class])
    abstract fun bindFavoriteActivity(): FavoriteActivity
}