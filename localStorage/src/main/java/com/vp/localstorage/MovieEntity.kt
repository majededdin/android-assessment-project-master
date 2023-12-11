package com.vp.localstorage

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
class MovieEntity(
    @PrimaryKey val imdbId: String,
    val title: String,
    val poster: String,
    var isFavorite: Boolean = false
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MovieEntity

        if (imdbId != other.imdbId) return false

        return true
    }

    override fun hashCode(): Int {
        return imdbId.hashCode()
    }
}