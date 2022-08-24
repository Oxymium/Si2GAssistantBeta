package com.oxymium.si2gassistant.features.addactor

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.si2gassistant.model.*
import com.oxymium.si2gassistant.repositories.ActorsRepository
import kotlinx.coroutines.launch

// --------------------
// ADD ACTOR VIEW MODEL
// --------------------

class AddActorViewModel(val actorsRepository: ActorsRepository): ViewModel() {

    // Snackbar
    val triggerCreatedActorNotification = MutableLiveData(false)

    // Lading/Success/Failed state
    val actorCreationStatus = MutableLiveData(CreationState.AWAITING)

    // Actor values
    val firstname = MutableLiveData<String>(null)
    val name = MutableLiveData<String>(null)
    val role = MutableLiveData<String>(null)
    val academy = MutableLiveData<Academy?>(null)

    suspend fun createActor(actor: Actor){
        viewModelScope.launch {
            actorsRepository.createActor(actor).collect{
                when (it){
                    is State.Loading -> {
                        Log.d("Add Actor", "Uploading...")
                        actorCreationStatus.value = CreationState.UPLOADING
                    }
                    is State.Success -> {
                        Log.d("Add Actor", "Success! ${it.data}")
                        triggerCreatedActorNotification.value = true
                        triggerCreatedActorNotification.value = false
                        actorCreationStatus.value = CreationState.SUCCESS
                    }
                    is State.Failed -> {
                        Log.d("Add Actor", "Failed.")
                        actorCreationStatus.value = CreationState.FAILURE

                    }
                }
            }
        }
    }

    fun pushActor(){
        viewModelScope.launch {
            createActor(
                Actor(
                    "",
                    name.value,
                    firstname.value,
                    role.value,
                    academy.value?.location,
                    listOf()
                )
            )
        }
    }

    val roleFieldClickedState = MutableLiveData(false)
    fun onClickRoleField(){
        roleFieldClickedState.value = true
    }

    val addActorButtonClicked = MutableLiveData(false)
    fun onClickAddActorButton(){
        addActorButtonClicked.value = true
    }


}