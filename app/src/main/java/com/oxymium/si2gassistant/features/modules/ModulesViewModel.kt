package com.oxymium.si2gassistant.features.modules

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.si2gassistant.model.Module
import com.oxymium.si2gassistant.model.State
import com.oxymium.si2gassistant.repositories.ModulesRepository
import kotlinx.coroutines.launch

// ------------------
// MODULES VIEW MODEL
// ------------------

class ModulesViewModel(val modulesRepository: ModulesRepository): ViewModel() {

    val allModules = MutableLiveData<List<Module>>()
    val validatedModules = MutableLiveData<List<String>>()

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
        allModules.value?.map { element1 -> validatedModules.value?.firstOrNull() { element1.validated == true } ?: element1 }
    }

}