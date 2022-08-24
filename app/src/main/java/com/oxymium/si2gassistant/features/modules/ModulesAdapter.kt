package com.oxymium.si2gassistant.features.modules

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.oxymium.si2gassistant.model.Module

// ----------------------------
// ModulesAdapter (RecyclerView)
// ----------------------------

class ModulesAdapter(private val isSuperUser: Boolean, private val moduleListener: ModuleListener): ListAdapter<Module, ModulesViewHolder>(ModuleDataAdapterListDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModulesViewHolder {
        return ModulesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ModulesViewHolder, position: Int) {
        holder.bind(getItem(position), moduleListener, isSuperUser)
    }

    // For smoother/faster lists comparison, also RecyclerView animation
    private class ModuleDataAdapterListDiff : DiffUtil.ItemCallback<Module>() {

        override fun areItemsTheSame(oldItem: Module, newItem: Module): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Module, newItem: Module): Boolean {
            return oldItem.id == newItem.id
        }
    }
}