package com.oxymium.si2gassistant.features.modules

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oxymium.si2gassistant.databinding.ItemModuleBinding
import com.oxymium.si2gassistant.model.Module

// --------------------------------
// ModulesViewHolder (RecyclerView)
// --------------------------------

class ModulesViewHolder(private val binding: ItemModuleBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(module: Module, moduleListener: ModuleListener, isSuperUser: Boolean) {

        // REQUIRED WITH DATA BINDING
        binding.executePendingBindings()

        // BIND DATA
        binding.module = module
        binding.moduleListener = moduleListener
        binding.isSuperUser = isSuperUser

    }

    companion object {

        fun from(parent: ViewGroup): ModulesViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemModuleBinding.inflate(layoutInflater, parent, false)
            return ModulesViewHolder(binding)
        }
    }
}