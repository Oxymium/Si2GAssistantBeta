package com.oxymium.si2gassistant.features.overview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.si2gassistant.model.Academy
import com.oxymium.si2gassistant.model.Actor
import com.oxymium.si2gassistant.model.Issue
import com.oxymium.si2gassistant.model.State
import com.oxymium.si2gassistant.repositories.AcademiesRepository
import com.oxymium.si2gassistant.repositories.ActorsRepository
import com.oxymium.si2gassistant.repositories.IssuesRepository
import kotlinx.coroutines.launch

// -------------------
// OVERVIEW VIEW MODEL
// -------------------

class OverviewViewModel(val issuesRepository: IssuesRepository, val actorsRepository: ActorsRepository, val academiesRepository: AcademiesRepository): ViewModel() {

    init {
        viewModelScope.launch {
            getAllIssues()
            getAllAcademies()
            getAllActors()
        }
    }

    // ------
    // ISSUES
    // ------

    val totalIssues = MutableLiveData<Int>()
    val totalWeakIssues = MutableLiveData<Int>()
    val totalModerateIssues = MutableLiveData<Int>()
    val totalCriticalIssues = MutableLiveData<Int>()

    suspend fun getAllIssues(){
        issuesRepository.getAllIssues().collect{
            when (it){
                is State.Loading -> {}
                is State.Success -> {
                    countIssues(it.data)
                }
                is State.Failed -> {}
            }
        }
    }

    private fun countIssues(issues: List<Issue>){
        // Total list size
        totalIssues.value = issues.size
        // Weak issues
        totalWeakIssues.value = issues.filter{ it.gravity == 1 }.size
        // Moderate issues
        totalModerateIssues.value = issues.filter{ it.gravity == 2 }.size
        // Critical issues
        totalCriticalIssues.value = issues.filter{ it.gravity == 3 }.size
    }

    // ---------
    // ACADEMIES
    // ---------

    val totalAcademies = MutableLiveData<Int>()

    suspend fun getAllAcademies(){
        academiesRepository.getAllAcademies().collect{
            when (it){
                is State.Loading -> {}
                is State.Success -> {
                    countAcademies(it.data)
                }
                is State.Failed -> {}
            }
        }
    }

    private fun countAcademies(academies: List<Academy>){
        // Total list size
        totalAcademies.value = academies.size

    }

    // ------
    // ACTORS
    // ------

    val totalActors = MutableLiveData<Int>()

    suspend fun getAllActors(){
        actorsRepository.getAllActors().collect{
            when (it){
                is State.Loading -> {}
                is State.Success -> {
                    countActors(it.data)
                }
                is State.Failed -> {}
            }
        }
    }

    private fun countActors(actors: List<Actor>){
        // Total list size
        totalActors.value = actors.size
    }
}