package p.vasylprokudin.israelit.presentation.view.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import kotlinx.android.synthetic.main.list_item.view.tvDate
import kotlinx.android.synthetic.main.list_item.view.tvComments
import kotlinx.android.synthetic.main.list_item.view.tvScore
import kotlinx.android.synthetic.main.list_item.view.tvAuthor
import kotlinx.android.synthetic.main.list_item.view.tvSubreddit
import kotlinx.android.synthetic.main.list_item.view.tvTitle
import p.vasylprokudin.israelit.R
import p.vasylprokudin.israelit.data.model.RawListing.RawData.RawChild
import p.vasylprokudin.israelit.presentation.view.list.listener.ItemClickedListener
import p.vasylprokudin.israelit.util.TimeConverter

class ListItemsAdapter : ListAdapter<RawChild, ListItemsAdapter.ViewHolder>(DIFFER) {

    companion object {
        private val DIFFER = object : DiffUtil.ItemCallback<RawChild>() {
            override fun areItemsTheSame(oldItem: RawChild, newItem: RawChild): Boolean =
                oldItem.data.id == newItem.data.id

            override fun areContentsTheSame(oldItem: RawChild, newItem: RawChild): Boolean =
                oldItem == newItem
        }
    }

    lateinit var itemsClickedListener: ItemClickedListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view, itemsClickedListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position).data)
    }

    inner class ViewHolder(
        private val view: View,
        private val itemsClickedListener: ItemClickedListener
    ) : RecyclerView.ViewHolder(view) {

        fun bind(item: RawChild.RawEntry) = with(view) {
            item.run {
                tvTitle.text = title
                tvSubreddit.text = subreddit
                tvAuthor.text = author
                tvScore.text = "$score"
                tvComments.text = "$comments"
                tvDate.text = TimeConverter.getTimeAgo(created)
            }
            setOnClickListener { itemsClickedListener.onItemRowClicked(item.url) }
        }
    }
}
