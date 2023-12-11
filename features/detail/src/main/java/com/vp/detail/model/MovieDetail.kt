package com.vp.detail.model

import com.google.gson.annotations.SerializedName
import com.vp.localstorage.MovieEntity

data class MovieDetail(
    @SerializedName("imdbID")
    val imdbId: String,
    @SerializedName("Title")
    val title: String,
    @SerializedName("Year")
    val year: String,
    @SerializedName("Runtime")
    val runtime: String,
    @SerializedName("Director")
    val director: String,
    @SerializedName("Plot")
    val plot: String,
    @SerializedName("Poster")
    val poster: String = ""
)

fun MovieDetail.toEntity() = MovieEntity(imdbId, title, poster)