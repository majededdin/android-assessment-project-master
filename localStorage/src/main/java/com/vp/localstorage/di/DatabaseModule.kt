package com.vp.localstorage.di

import android.app.Application
import androidx.room.Room
import com.vp.localstorage.DataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun providesDatabase(application: Application): DataBase =
        Room.databaseBuilder(application, DataBase::class.java, "movie_database").build()

    @Singleton
    @Provides
    fun providesMoviesDao(database: DataBase) = database.moviesDao()
}