package com.oxymium.si2gassistant.repositories

import com.oxymium.si2gassistant.model.Academy
import com.oxymium.si2gassistant.model.State
import kotlinx.coroutines.flow.Flow

// -------------------
// AcademiesRepository
// -------------------

interface AcademiesRepository {

    // Get Academy given User ID
    suspend fun getAcademyWithGivenUserId(id: String): Flow<State<Academy?>>
}