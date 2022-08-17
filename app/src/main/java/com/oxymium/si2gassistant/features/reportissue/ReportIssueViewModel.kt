package com.oxymium.si2gassistant.features.reportissue

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.si2gassistant.model.Issue
import com.oxymium.si2gassistant.model.State
import com.oxymium.si2gassistant.repositories.IssuesRepository
import kotlinx.coroutines.launch

// -----------------------
// REPORT ISSUE VIEW MODEL
// -----------------------

class ReportIssueViewModel(val issuesRepository: IssuesRepository): ViewModel() {

    val gravity = MutableLiveData<Float>(1f)
    val category = MutableLiveData<String?>(null)
    val description = MutableLiveData<String?>(null)

    // Create a new Issue
    suspend fun createIssue(issue: Issue) {
        viewModelScope.launch {
            issuesRepository.createIssue(issue).collect {
                when (it) {
                    is State.Loading -> {}
                    is State.Success -> { println(category.value)}
                    is State.Failed -> {}
                }
            }
        }
    }

    fun onCreateIssueButton(){
        viewModelScope.launch {
            createIssue(Issue("", "", "", 1, "", 1, ""))
        }
    }

}