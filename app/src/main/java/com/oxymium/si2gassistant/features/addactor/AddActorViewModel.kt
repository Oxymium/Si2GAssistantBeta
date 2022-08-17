package com.oxymium.si2gassistant.features.addactor

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.si2gassistant.model.Actor
import com.oxymium.si2gassistant.model.State
import com.oxymium.si2gassistant.repositories.ActorsRepository
import kotlinx.coroutines.launch

// --------------------
// ADD ACTOR VIEW MODEL
// --------------------

class AddActorViewModel(val actorsRepository: ActorsRepository): ViewModel() {

    // Actor values
    val firstname = MutableLiveData<String>(null)
    val name = MutableLiveData<String>(null)
    val role = MutableLiveData<String>(null)

    suspend fun createActor(actor: Actor){
        viewModelScope.launch {
            actorsRepository.createActor(actor).collect{
                when (it){
                    is State.Loading -> Log.d("Add Actor", "Uploading...")
                    is State.Success -> Log.d("Add Actor", "Success!")
                    is State.Failed -> Log.d("Add Actor", "Failed.")
                }
            }
        }
    }

    val roleFieldClickedState = MutableLiveData<Boolean>(false)
    fun onClickRoleField(){
        roleFieldClickedState.value = true
    }

    val addActorButtonClicked = MutableLiveData<Boolean>(false)
    fun onClickAddActorButton(){
        addActorButtonClicked.value = true
    }


}