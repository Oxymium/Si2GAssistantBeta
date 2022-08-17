package com.oxymium.si2gassistant.features.issues

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oxymium.si2gassistant.model.Issue
import com.oxymium.si2gassistant.model.State
import com.oxymium.si2gassistant.repositories.IssuesRepository

// ------------------
// ISSUES VIEW MODEL
// ------------------

class IssuesViewModel(val issuesRepository: IssuesRepository): ViewModel() {

    val allIssues = MutableLiveData<List<Issue>>()
    val filteredIssues = MutableLiveData<List<Issue>>()

    suspend fun getAllIssues() {
        issuesRepository.getAllIssues().collect {
            when (it) {
                is State.Loading -> Log.d("Issues (ALL):", "Loading...")
                is State.Success -> {
                    Log.d("Issues (ALL):", "Success!")
                    allIssues.value = it.data
                    filteredIssues.value = it.data
                }
                is State.Failed -> {
                    Log.d("Issues:", "Failure! = $it")
                }
            }
        }
    }

    suspend fun getIssuesByAcademyId(academyId: String){
        issuesRepository.getAllIssuesByAcademy(academyId).collect {
            when (it) {
                is State.Loading -> Log.d("Issues (Academy):", "Loading...")
                is State.Success -> {
                    Log.d("Issues (Academy):", "Success! + ${it.data.size}")
                    allIssues.value = it.data
                    filteredIssues.value = it.data
                }
                is State.Failed -> Log.d("Issues (Academy):", "Failure! = $it")
            }
        }
    }

    // Default = 0 = Descending order. 1 = Ascending order
    val dateSortingState = MutableLiveData<Int>(0)

    fun onClickSortingDateButton() {
        when (dateSortingState.value) {
            0 -> {
                dateSortingState.value = 1
                filteredIssues.value = filteredIssues.value?.sortedByDescending { it.date }
            }
            1 -> {
                dateSortingState.value = 0
                filteredIssues.value = filteredIssues.value?.sortedBy { it.date }
            }
        }
    }

    fun onClickSortingSolvedButton(which: Int?){
        when (which){
            // Both
            0 -> {}
            // Not solved sorting
            1 -> filteredIssues.value = allIssues.value?.filter { it.solved == false }
            // Solved sorting
            2 -> filteredIssues.value = allIssues.value?.filter { it.solved == true }

        }
    }

    fun onClickSortingGravityButton(which: Int?) {
        when (which){
            0 -> filteredIssues.value = allIssues.value
            1 -> filteredIssues.value = allIssues.value?.filter { it.gravity == 1 }
            2 -> filteredIssues.value = allIssues.value?.filter { it.gravity == 2 }
            3 -> filteredIssues.value = allIssues.value?.filter { it.gravity == 3 }
        }
    }

    // Quick search
    val quickSearchIssues = MutableLiveData<String?>(null)

    fun quickSearchIssues(quickSearch: String?) {

        if (quickSearch.isNullOrEmpty()) {
            filteredIssues.value = allIssues.value
        }

        if (quickSearch != null) {
            filteredIssues.value = allIssues.value?.filter {
                it.category?.contains(quickSearch, true) == true ||
                        it.description?.contains(quickSearch, true) == true ||
                        it.academyLocation?.contains(quickSearch, true) == true

            }

        }
    }

    // Handle search area visibility
    val searchIssuesAreaState = MutableLiveData<Boolean>(false)
    fun onClickSearchButton(){
        when (searchIssuesAreaState.value){
            false -> searchIssuesAreaState.value = true
            true -> searchIssuesAreaState.value = false
            else -> {}
        }
    }

}