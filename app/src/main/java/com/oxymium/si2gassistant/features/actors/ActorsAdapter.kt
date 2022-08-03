package com.oxymium.si2gassistant.features.actors

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.oxymium.si2gassistant.model.Actor

// ----------------------------
// ActorsAdapter (RecyclerView)
// ----------------------------

class ActorsAdapter(private val actorListener: ActorListener): ListAdapter<Actor, ActorsViewHolder>(ActorDataAdapterListDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorsViewHolder {
        return ActorsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ActorsViewHolder, position: Int) {
        holder.bind(getItem(position), actorListener)
    }

    // For smoother/faster lists comparison, also RecyclerView animation
    private class ActorDataAdapterListDiff : DiffUtil.ItemCallback<Actor>() {

        override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Actor, newItem: Actor): Boolean {
            return oldItem.id == newItem.id
        }
    }
}