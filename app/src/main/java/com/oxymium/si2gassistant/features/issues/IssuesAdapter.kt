package com.oxymium.si2gassistant.features.issues

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.oxymium.si2gassistant.model.Issue

// ----------------------------
// IssuesAdapter (RecyclerView)
// ----------------------------

class IssuesAdapter(private val issuesListener: IssueListener): ListAdapter<Issue, IssuesViewHolder>(IssuesAdapter.IssueDataAdapterListDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssuesViewHolder {
        return IssuesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: IssuesViewHolder, position: Int) {
        holder.bind(getItem(position), issuesListener)
    }

    // For smoother/faster lists comparison, also RecyclerView animation
    private class IssueDataAdapterListDiff : DiffUtil.ItemCallback<Issue>() {

        override fun areItemsTheSame(oldItem: Issue, newItem: Issue): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Issue, newItem: Issue): Boolean {
            return oldItem.id == newItem.id
        }
    }
}