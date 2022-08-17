package com.oxymium.si2gassistant.repositories

import com.google.firebase.firestore.DocumentReference
import com.oxymium.si2gassistant.model.Actor
import com.oxymium.si2gassistant.model.State
import kotlinx.coroutines.flow.Flow

interface ActorsRepository {

    // Get all Actors
    suspend fun getAllActors(): Flow<State<List<Actor>>>

    // Get Actors by given Academy ID
    suspend fun getActorsByAcademyId(academyId: String?): Flow<State<List<Actor>>>

    // Create an Actor
    suspend fun createActor(actor: Actor): Flow<State<DocumentReference>>

    // Update Actor - add Module id to array
    suspend fun addValidatedActorModule(actorId: String, moduleId: String): Flow<State<Void>>

    // Update Actor - remove Module id to array
    suspend fun removeValidatedActorModules(actorId: String, moduleId: String): Flow<State<Void>>

}