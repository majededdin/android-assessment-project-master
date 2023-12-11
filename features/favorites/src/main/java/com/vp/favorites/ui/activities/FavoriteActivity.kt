package com.vp.favorites.ui.activities

import android.content.Intent
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.net.Uri
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vp.favorites.R
import com.vp.favorites.ui.adapters.FavoritesAdapter
import com.vp.favorites.viewModel.FavoritesViewModel
import com.vp.localstorage.MovieEntity
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class FavoriteActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var viewModel: FavoritesViewModel
    private lateinit var noFavoritesText: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FavoritesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        initViews()
        initViewModel()
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.recyclerView)
        noFavoritesText = findViewById(R.id.noFavoritesText)
        recyclerView.layoutManager = GridLayoutManager(
            this,
            if (resources.configuration.orientation == ORIENTATION_PORTRAIT) 2 else 3
        )

        adapter = FavoritesAdapter()
        adapter.onItemClickListener = ::onItemClick
        recyclerView.adapter = adapter
    }

    private fun onItemClick(imdbId: String) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("app://movies/detail").buildUpon().appendQueryParameter("imdbID", imdbId)
                    .build()
            ).setPackage(packageName)
        )
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, factory)[FavoritesViewModel::class.java]
        viewModel.favorites().observe(this) { handleResult(it) }
    }

    private fun handleResult(movies: List<MovieEntity>) {
        if (movies.isNotEmpty()) {
            showFavorites(movies)
        } else {
            showNoFavoritesText()
        }
    }

    private fun showFavorites(movies: List<MovieEntity>) {
        noFavoritesText.visibility = GONE
        recyclerView.visibility = VISIBLE
        adapter.setItems(movies)
    }

    private fun showNoFavoritesText() {
        noFavoritesText.visibility = VISIBLE
        recyclerView.visibility = GONE
    }
}