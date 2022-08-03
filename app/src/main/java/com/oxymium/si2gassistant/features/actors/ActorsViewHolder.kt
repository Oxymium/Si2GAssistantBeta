package com.oxymium.si2gassistant.features.actors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oxymium.si2gassistant.databinding.ItemActorBinding
import com.oxymium.si2gassistant.model.Actor

// -------------------------------
// ActorsViewHolder (RecyclerView)
// -------------------------------

class ActorsViewHolder(private val binding: ItemActorBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(actor: Actor, actorListener: ActorListener) {

        // REQUIRED WITH DATA BINDING
        binding.executePendingBindings()

        // BIND DATA
        binding.actor = actor
        binding.actorListener = actorListener
    }

    companion object {

        fun from(parent: ViewGroup): ActorsViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemActorBinding.inflate(layoutInflater, parent, false)
            return ActorsViewHolder(binding)
        }
    }
}