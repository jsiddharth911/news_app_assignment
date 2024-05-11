package com.example.newsappassignment.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.newsappassignment.R
import com.example.newsappassignment.data.database.entity.NewsArticleEntity
import com.squareup.picasso.Picasso

/**
 * Adapter class for displaying news articles in a RecyclerView.
 * @param article The list of news articles to display.
 */
class NewsAdapter(private var article: List<NewsArticleEntity>):
    Adapter<NewsAdapter.ItemViewHolder>() {

    /**
     * Sorts the dataset based on the selected position.
     * @param position The position indicating the sorting order.
     */
    fun sortDataSet(position: Int) {
        article = if(position == 0) {
            article.sortedBy { it.publishedAt }
        } else {
            article.sortedByDescending { it.publishedAt }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_items, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return article.size
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val pos = article[position]
        if(!pos.urlToImage.isNullOrEmpty()) {
            Picasso.get().load(pos.urlToImage).into(holder.image)
        }
        holder.title.text = pos.title
        holder.description.text = pos.description
        holder.author.text = pos.author

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, MainActivity2::class.java)
            intent.putExtra("article_url", pos.url)
            context.startActivity(intent)
        }
    }

    /**
     * ViewHolder class for holding views of news items.
     */
    class ItemViewHolder(itemView: View): ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
        val title: TextView = itemView.findViewById(R.id.title)
        val description: TextView = itemView.findViewById(R.id.description)
        val author: TextView = itemView.findViewById(R.id.author)
    }
}