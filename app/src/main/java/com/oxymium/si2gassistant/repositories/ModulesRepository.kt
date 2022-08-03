package com.oxymium.si2gassistant.repositories

import com.oxymium.si2gassistant.model.Module
import com.oxymium.si2gassistant.model.State
import kotlinx.coroutines.flow.Flow

// ------------------
// MODULES REPOSITORY
// ------------------

interface ModulesRepository {

    // Get all Modules
    suspend fun getAllModules(): Flow<State<List<Module>>>
}