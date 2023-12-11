package com.vp.localstorage

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class DataBase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao
}