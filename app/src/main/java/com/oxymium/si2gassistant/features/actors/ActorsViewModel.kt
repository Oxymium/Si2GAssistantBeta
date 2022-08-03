package com.oxymium.si2gassistant.features.actors

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.si2gassistant.model.Actor
import com.oxymium.si2gassistant.model.State
import com.oxymium.si2gassistant.repositories.ActorsRepository
import kotlinx.coroutines.launch

// -----------------
// ACTORS VIEW MODEL
// -----------------

class ActorsViewModel(val actorsRepository: ActorsRepository): ViewModel() {

    val actorsByGivenAcademyId = MutableLiveData<List<Actor>>()

    val filteredActors = MutableLiveData<List<Actor>>()

    suspend fun getActorsByAcademyId(academyId: String?) {

        viewModelScope.launch {
            actorsRepository.getActorsByAcademyId(academyId).collect {
                when (it) {
                    is State.Loading -> Log.d("Actors:", "Loading...")
                    is State.Success -> {
                        Log.d("Actors:", "Success!")
                        Log.d("Actors:", "${it.data.size}")
                        actorsByGivenAcademyId.value = it.data
                        filteredActors.value = it.data
                    }
                    is State.Failed -> {
                        Log.d("Actors:", "$it")
                    }
                }
            }
        }
    }
}