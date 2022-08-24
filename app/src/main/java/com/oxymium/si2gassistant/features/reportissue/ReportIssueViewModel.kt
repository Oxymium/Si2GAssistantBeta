package com.oxymium.si2gassistant.features.reportissue

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.si2gassistant.model.*
import com.oxymium.si2gassistant.repositories.IssuesRepository
import kotlinx.coroutines.launch
import java.util.Calendar

// -----------------------
// REPORT ISSUE VIEW MODEL
// -----------------------

class ReportIssueViewModel(val issuesRepository: IssuesRepository): ViewModel() {

    // Notification
    val triggerCreatedIssueNotification = MutableLiveData(false)

    val issueCreationState = MutableLiveData<CreationState>(CreationState.AWAITING)

    // Issue values
    val academy = MutableLiveData<Academy?>(null)
    val category = MutableLiveData<String?>(null)
    val gravity = MutableLiveData<Float>(1f)
    val description = MutableLiveData<String?>(null)

    // Date
    private fun getCurrentDateInMillis() = Calendar.getInstance().timeInMillis

    // Create a new Issue
    suspend fun createIssue(issue: Issue) {
        viewModelScope.launch {
            issuesRepository.createIssue(issue).collect {
                when (it) {
                    is State.Loading -> {
                        Log.d("Create Issue", "Uploading...")
                        issueCreationState.value = CreationState.UPLOADING
                    }
                    is State.Success -> {
                        Log.d("Create Issue", "Success! ${it.data.id}")
                        triggerCreatedIssueNotification.value = true
                        triggerCreatedIssueNotification.value = false
                        issueCreationState.value = CreationState.SUCCESS
                    }
                    is State.Failed -> {
                        Log.d("Create Issue", "Failed")
                        issueCreationState.value = CreationState.FAILURE
                    }
                }
            }
        }
    }

    fun pushIssue(){
        viewModelScope.launch {
            createIssue(
                Issue(
                    "",
                    academy.value?.id,
                    academy.value?.location,
                    getCurrentDateInMillis(),
                    category.value,
                    gravity.value?.toInt(),
                    description.value,
                    false
                ))
        }
    }

    val categoryFieldClickedState = MutableLiveData(false)
    fun onClickCategoryField(){
        categoryFieldClickedState.value = true
    }

    val reportIssueButtonClicked = MutableLiveData(false)
    fun onClickReportIssueButton(){
        reportIssueButtonClicked.value = true
    }
}