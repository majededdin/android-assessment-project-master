package com.vp.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.vp.detail.DetailActivity
import com.vp.detail.model.MovieDetail
import com.vp.detail.model.repositories.DetailRepository
import com.vp.detail.service.DetailService
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject
import javax.security.auth.callback.Callback

class DetailsViewModel @Inject constructor(
    private val detailService: DetailService,
    private val repository: DetailRepository
) : ViewModel() {

    private val details: MutableLiveData<MovieDetail> = MutableLiveData()
    private val title: MutableLiveData<String> = MutableLiveData()
    private val loadingState: MutableLiveData<LoadingState> = MutableLiveData()
    private var favorite: LiveData<Boolean> = MutableLiveData()

    fun title(): LiveData<String> = title

    fun details(): LiveData<MovieDetail> = details

    fun state(): LiveData<LoadingState> = loadingState

    fun favorite(): LiveData<Boolean> = favorite

    fun fetchDetails() {
        val imdbId = DetailActivity.queryProvider.getMovieId()

        loadingState.value = LoadingState.IN_PROGRESS
        detailService.getMovie(imdbId).enqueue(object : Callback, retrofit2.Callback<MovieDetail> {
            override fun onResponse(call: Call<MovieDetail>, response: Response<MovieDetail>) {
                details.postValue(response.body())

                response.body()?.title?.let {
                    title.postValue(it)
                }

                loadingState.value = LoadingState.LOADED
            }

            override fun onFailure(call: Call<MovieDetail>, t: Throwable) {
                details.postValue(null)
                loadingState.value = LoadingState.ERROR
            }
        })

        favorite = Transformations.map(repository.getMovieById(imdbId)) { it?.isFavorite ?: false }
    }

    fun toggleFavorite() {
        val newFavoriteValue = favorite.value?.not() ?: true
        details.value?.let { repository.saveMovie(it, newFavoriteValue) }
    }

    enum class LoadingState {
        IN_PROGRESS, LOADED, ERROR
    }
}