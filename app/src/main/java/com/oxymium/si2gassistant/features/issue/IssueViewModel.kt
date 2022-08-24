package com.oxymium.si2gassistant.features.issue

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.si2gassistant.model.Issue
import com.oxymium.si2gassistant.model.State
import com.oxymium.si2gassistant.repositories.IssuesRepository
import kotlinx.coroutines.launch

// ----------------
// ISSUE VIEW MODEL
// ----------------

class IssueViewModel(val issuesRepository: IssuesRepository): ViewModel() {

    // ISSUE
    val issue = MutableLiveData<Issue?>(null)

    fun getIssueById(issueId: String){
        viewModelScope.launch {
            issuesRepository.getIssueById(issueId).collect{
                when (it){
                    is State.Loading -> Log.d("Issue:", "Updating...")
                    is State.Success -> {
                        Log.d("Issue:", "Success!")
                        issue.value = it.data
                    }
                    is State.Failed -> Log.d("Issue:", "Failed ${it.message}")
                }
            }
        }
    }

    fun onClickUpdateIssue(issueId: String){
        viewModelScope.launch {
            issuesRepository.updateIssue(issueId).collect{
                when (it){
                    is State.Loading -> Log.d("Issue: Update", "Updating...")
                    is State.Success -> Log.d("Issue: Update", "Success!")
                    is State.Failed -> Log.d("Issue: Update", "Failed ${it.message}")
                }
            }
        }
    }

    val issueUpdateClicked = MutableLiveData<Boolean>(false)
    fun onClickUpdateIssue(){
        issueUpdateClicked.value = true
    }

}