package com.oxymium.si2gassistant.repositories

import com.oxymium.si2gassistant.model.Actor
import com.oxymium.si2gassistant.model.State
import kotlinx.coroutines.flow.Flow

interface ActorsRepository {

    // Get all Actors
    suspend fun getAllActors(): Flow<State<List<Actor>>>

    // Get Actors by given Academy ID
    suspend fun getActorsByAcademyId(academyId: String?): Flow<State<List<Actor>>>

}