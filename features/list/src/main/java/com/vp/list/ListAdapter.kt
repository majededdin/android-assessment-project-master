package com.vp.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.vp.list.model.ListItem

@SuppressLint("NotifyDataSetChanged")
class ListAdapter(
    private val onItemClickListener: (String) -> Unit
) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    var listItems: List<ListItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item: ListItem = listItems[position]
        if (item.poster != NO_IMAGE) {
            val density = holder.imageView.resources.displayMetrics.density
            GlideApp.with(holder.imageView.context)
                .load(item.poster)
                .override((300 * density).toInt(), (600 * density).toInt())
                .into(holder.imageView)
        } else {
            holder.imageView.setImageResource(R.drawable.placeholder)
        }

        holder.imageView.setOnClickListener {
            onItemClickListener(item.imdbID)
        }
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    fun clearItems() {
        listItems = emptyList()
    }


    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.poster)
    }

    interface OnItemClickListener {
        fun onItemClick(imdbID: String)
    }

    companion object {
        private const val NO_IMAGE = "N/A"
    }
}