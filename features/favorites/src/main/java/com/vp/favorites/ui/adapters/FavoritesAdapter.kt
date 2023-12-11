package com.vp.favorites.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.vp.favorites.R
import com.vp.list.GlideApp
import com.vp.localstorage.MovieEntity

@SuppressLint("NotifyDataSetChanged")
class FavoritesAdapter : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    private val items = arrayListOf<MovieEntity>()
    var onItemClickListener: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setItems(newItems: List<MovieEntity>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val posterImageView: ImageView
        private val density: Float
        private val NO_IMAGE = "N/A"

        init {
            posterImageView = itemView.findViewById(R.id.poster)
            density = itemView.resources.displayMetrics.density
            itemView.setOnClickListener { onItemClickListener?.invoke(items[bindingAdapterPosition].imdbId) }
        }

        fun bind(item: MovieEntity) {
            if (item.poster == NO_IMAGE) {
                posterImageView.setImageResource(R.drawable.placeholder)
            } else {
                GlideApp.with(posterImageView).load(item.poster).placeholder(R.drawable.placeholder)
                    .override((300 * density).toInt(), (600 * density).toInt())
                    .into(posterImageView)
            }
        }
    }
}