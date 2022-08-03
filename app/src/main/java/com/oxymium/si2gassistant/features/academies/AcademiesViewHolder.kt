package com.oxymium.si2gassistant.features.academies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oxymium.si2gassistant.databinding.ItemAcademyBinding
import com.oxymium.si2gassistant.model.Academy

// ----------------------------------
// AcademiesViewHolder (RecyclerView)
// ----------------------------------

class AcademiesViewHolder(private val binding: ItemAcademyBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(academy: Academy, academyListener: AcademyListener) {

        // REQUIRED WITH DATA BINDING
        binding.executePendingBindings()

        // BIND DATA
        binding.academy = academy
        binding.academyListener = academyListener
    }

    companion object {

        fun from(parent: ViewGroup): AcademiesViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemAcademyBinding.inflate(layoutInflater, parent, false)
            return AcademiesViewHolder(binding)
        }
    }
}