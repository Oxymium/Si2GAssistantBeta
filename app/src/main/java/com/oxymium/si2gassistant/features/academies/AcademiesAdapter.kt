package com.oxymium.si2gassistant.features.academies

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.oxymium.si2gassistant.model.Academy

// -------------------------------
// AcademiesAdapter (RecyclerView)
// ------------------------------

class AcademiesAdapter(private val academyListener: AcademyListener): ListAdapter<Academy, AcademiesViewHolder>(AcademyDataAdapterListDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AcademiesViewHolder {
        return AcademiesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AcademiesViewHolder, position: Int) {
        holder.bind(getItem(position), academyListener)
    }

    // For smoother/faster lists comparison, also RecyclerView animation
    private class AcademyDataAdapterListDiff : DiffUtil.ItemCallback<Academy>() {

        override fun areItemsTheSame(oldItem: Academy, newItem: Academy): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Academy, newItem: Academy): Boolean {
            return oldItem.id == newItem.id
        }
    }
}