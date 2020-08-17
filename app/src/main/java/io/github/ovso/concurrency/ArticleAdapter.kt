package io.github.ovso.concurrency

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.ovso.concurrency.data.Article
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_main.view.*

class ArticleAdapter : ListAdapter<Article, ArticleAdapter.ViewHolder>(DIFF_UTIL) {
    val articles = mutableListOf<Article>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_main, parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindViewHolder(getItem(position))
    }


    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun onBindViewHolder(item: Article) {
            itemView.tvTitle.text = item.title
            itemView.tvFeed.text = item.feed
            itemView.tvSummary.text = item.summary
        }

    }
}

val DIFF_UTIL = object : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
        oldItem.title == newItem.title

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
        areItemsTheSame(oldItem, newItem)

}