package com.vp.localstorage

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movie WHERE isFavorite=1")
    fun getFavoriteMovies(): LiveData<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMovie(movie: MovieEntity)

    @Query("SELECT * FROM movie WHERE imdbId=:imdbId")
    fun getMovieById(imdbId: String): LiveData<MovieEntity>

}