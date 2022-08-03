package com.oxymium.si2gassistant.features.academies

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.si2gassistant.model.Academy
import com.oxymium.si2gassistant.model.State
import com.oxymium.si2gassistant.repositories.AcademiesRepository
import kotlinx.coroutines.launch

// --------------------
// ACADEMIES VIEW MODEL
// --------------------

class AcademiesViewModel(val academiesRepository: AcademiesRepository): ViewModel() {

    val allAcademies = MutableLiveData<List<Academy>>()
    val filteredAcademies = MutableLiveData<List<Academy>>()

    init{ viewModelScope.launch { getAllAcademies() } }

    private suspend fun getAllAcademies() {
        academiesRepository.getAllAcademies().collect {
            when (it) {
                is State.Loading -> Log.d("Academies:", "Loading...")
                is State.Success -> {
                    Log.d("Academies:", "Success!")
                    Log.d("Academies:", "${it.data.size}")
                    allAcademies.value = it.data
                    filteredAcademies.value = it.data
                }
                is State.Failed -> Log.d("Academies:", "Failed")

            }
        }
    }

    fun onClickSortAcademiesByLocationButton(){
        filteredAcademies.value = filteredAcademies.value?.sortedBy { it.location } }

    fun onClickSortAcademiesByIdButton(){
        filteredAcademies.value = filteredAcademies.value?.sortedBy { it.id }
    }

    // Quick search
    val quickSearchAcademies = MutableLiveData<String?>(null)

    fun quickSearchAcademies(quickSearch: String?) {

        if (quickSearch.isNullOrEmpty()){
            filteredAcademies.value = allAcademies.value
        }

        if (quickSearch != null) {
            filteredAcademies.value = allAcademies.value?.filter {
                    it.location?.contains(quickSearch, true) == true || it.id?.contains(quickSearch, true) == true
                }
        }

    }

}