package com.oxymium.si2gassistant.features.issues

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oxymium.si2gassistant.databinding.ItemIssueBinding
import com.oxymium.si2gassistant.model.Issue

// -------------------------------
// IssuesViewHolder (RecyclerView)
// -------------------------------

class IssuesViewHolder(private val binding: ItemIssueBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(issue: Issue, issueListener: IssueListener) {

        // REQUIRED WITH DATA BINDING
        binding.executePendingBindings()

        // BIND DATA
        binding.issue = issue
        binding.issueListener = issueListener
    }

    companion object {

        fun from(parent: ViewGroup): IssuesViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemIssueBinding.inflate(layoutInflater, parent, false)
            return IssuesViewHolder(binding)
        }
    }
}