package me.tatarka.inject.ponyinject.episodes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import me.tatarka.inject.ponyinject.R
import me.tatarka.inject.ponyinject.api.Episode
import me.tatarka.inject.ponyinject.databinding.EpisodesItemBinding

typealias OnItemClick = (id: Int) -> Unit

/**
 * Shows a list of episodes.
 */
class EpisodesAdapter(private val onItemClick: OnItemClick) :
    PagingDataAdapter<Episode, EpisodesAdapter.ViewHolder>(
        object : DiffUtil.ItemCallback<Episode>() {
            override fun areItemsTheSame(oldItem: Episode, newItem: Episode) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Episode, newItem: Episode) = oldItem == newItem
        }
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.episodes_item, parent, false),
            onItemClick
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(view: View, onItemClick: OnItemClick) : RecyclerView.ViewHolder(view) {
        private val binding = EpisodesItemBinding.bind(view)
        private var episode: Episode? = null

        init {
            binding.root.setOnClickListener {
                episode?.let { onItemClick(it.id) }
            }
        }

        fun bind(episode: Episode?) {
            this.episode = episode
            binding.image.load(episode?.image) {
                crossfade(true)
            }
            binding.title.text = episode?.name
        }
    }
}