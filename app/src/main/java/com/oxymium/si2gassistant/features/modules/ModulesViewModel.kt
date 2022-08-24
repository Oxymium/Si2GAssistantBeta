package com.oxymium.si2gassistant.features.modules

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.si2gassistant.model.Module
import com.oxymium.si2gassistant.model.State
import com.oxymium.si2gassistant.repositories.ActorsRepository
import com.oxymium.si2gassistant.repositories.ModulesRepository
import kotlinx.coroutines.launch

// ------------------
// MODULES VIEW MODEL
// ------------------

class ModulesViewModel(val modulesRepository: ModulesRepository, val actorsRepository: ActorsRepository): ViewModel() {

    // All initial modules
    val allModules = MutableLiveData<List<Module>>()
    // Values of modules that are validated
    val validatedModules = MutableLiveData<List<String>>()
    // Final values to pass
    val finalModules = MutableLiveData<List<Module>>()

    init { viewModelScope.launch { getAllModules() } }

    private suspend fun getAllModules() {
        modulesRepository.getAllModules().collect{
            when (it) {
                is State.Loading -> Log.d("Modules:", "Loading...")
                is State.Success -> {
                    Log.d("Modules:", "Success!")
                    Log.d("Modules:", "${it.data.size}")
                    allModules.value = it.data
                }
                is State.Failed -> {
                    Log.d("Modules:", "$it")
                }
            }
        }
    }

    fun updateAllModulesWithValidatedModules() {

        println("${allModules.value}")
        println("${validatedModules.value}")

        finalModules.value = allModules.value?.map{
            if (validatedModules.value?.contains(it.id) == true) {
                it.copy(validated = true)
            }else{
                it
            }
        }
    }

    val moduleToUpdate = MutableLiveData<Module?>(null)

    fun addActorValidatedModule(actorId: String?, moduleId: String?){
        viewModelScope.launch {
            actorsRepository.addValidatedActorModule(actorId ?: "", moduleId ?: "").collect {
                when (it) {
                    is State.Loading -> Log.d("Actor add modules:", "Uploading...")
                    is State.Success -> Log.d("Actor add modules:", "Success!")
                    is State.Failed -> Log.d("Actor add modules:", "Failure. $it")
                }
            }
        }
    }

    fun removeActorValidatedModule(actorId: String?, moduleId: String?){
        viewModelScope.launch {
            actorsRepository.removeValidatedActorModules(actorId ?: "", moduleId ?: "").collect{
                when (it){
                    is State.Loading -> Log.d("Actor remove modules:", "Uploading...")
                    is State.Success -> Log.d("Actor remove modules:", "Success!")
                    is State.Failed -> Log.d("Actor remove modules:", "Failure. $it")
                }
            }
        }

    }


}