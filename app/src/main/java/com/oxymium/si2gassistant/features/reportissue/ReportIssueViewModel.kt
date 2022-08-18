package com.oxymium.si2gassistant.features.reportissue

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.si2gassistant.model.Academy
import com.oxymium.si2gassistant.model.Issue
import com.oxymium.si2gassistant.model.State
import com.oxymium.si2gassistant.repositories.IssuesRepository
import kotlinx.coroutines.launch

// -----------------------
// REPORT ISSUE VIEW MODEL
// -----------------------

class ReportIssueViewModel(val issuesRepository: IssuesRepository): ViewModel() {

    // Notification
    val triggerCreatedIssueNotification = MutableLiveData(false)

    // Issue values
    val academy = MutableLiveData<Academy?>(null)
    val category = MutableLiveData<String?>(null)
    val gravity = MutableLiveData<Float>(1f)
    val description = MutableLiveData<String?>(null)

    // Create a new Issue
    suspend fun createIssue(issue: Issue) {
        viewModelScope.launch {
            issuesRepository.createIssue(issue).collect {
                when (it) {
                    is State.Loading -> Log.d("Create Issue", "Uploading...")
                    is State.Success -> {
                        Log.d("Create Issue", "Success! ${it.data.id}")
                        triggerCreatedIssueNotification.value = true
                        triggerCreatedIssueNotification.value = false
                    }
                    is State.Failed -> Log.d("Create Issue", "Failed")
                }
            }
        }
    }

    fun onClickCreateIssueButton(){
        viewModelScope.launch {
            createIssue(
                Issue(
                    "",
                    academy.value?.id,
                    academy.value?.location,
                    1,
                    category.value,
                    gravity.value?.toInt(),
                    description.value,
                    false
                ))
        }
    }

}