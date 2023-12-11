package com.vp.detail.model.repositories

import com.vp.detail.model.MovieDetail
import com.vp.detail.model.toEntity
import com.vp.localstorage.MoviesDao
import java.util.concurrent.Executors
import javax.inject.Inject

class DetailRepository @Inject constructor(private val dao: MoviesDao) {

    fun saveMovie(movie: MovieDetail, isFavorite: Boolean) {
        Executors.newSingleThreadExecutor().execute {
            dao.saveMovie(movie.toEntity().apply { this.isFavorite = isFavorite })
        }
    }

    fun getMovieById(imdbId: String) = dao.getMovieById(imdbId)

}