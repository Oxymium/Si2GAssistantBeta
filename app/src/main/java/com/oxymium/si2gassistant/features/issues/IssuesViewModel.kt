package com.oxymium.si2gassistant.features.issues

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.si2gassistant.model.Issue
import com.oxymium.si2gassistant.model.IssuesSorting
import com.oxymium.si2gassistant.model.State
import com.oxymium.si2gassistant.repositories.IssuesRepository
import kotlinx.coroutines.launch

// ------------------
// ISSUES VIEW MODEL
// ------------------

class IssuesViewModel(val issuesRepository: IssuesRepository): ViewModel() {

    val allIssues = MutableLiveData<List<Issue>>()

    init{ viewModelScope.launch { getAllIssues() } }

    private suspend fun getAllIssues() {
        issuesRepository.getAllIssues().collect() {
            when (it) {
                is State.Loading -> {
                    println("Issues: LOADING")
                }
                is State.Success -> {
                    println("Issues: SUCCESS")
                    allIssues.value = it.data
                }
                is State.Failed -> {
                    println("Issues: FAILURE" + it.message.toString())
                }
            }
        }
    }
}