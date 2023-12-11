package com.vp.favorites.model

import androidx.lifecycle.LiveData
import com.vp.localstorage.MovieEntity
import com.vp.localstorage.MoviesDao
import javax.inject.Inject

class FavoriteRepository @Inject constructor(private val dao: MoviesDao) {

    fun getFavoriteMovies(): LiveData<List<MovieEntity>> =
        dao.getFavoriteMovies()
}