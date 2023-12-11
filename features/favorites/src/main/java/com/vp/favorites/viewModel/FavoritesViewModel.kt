package com.vp.favorites.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.vp.favorites.model.FavoriteRepository
import com.vp.localstorage.MovieEntity
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(repository: FavoriteRepository) : ViewModel() {

    private val favorites: LiveData<List<MovieEntity>> = repository.getFavoriteMovies()

    fun favorites() = favorites
}