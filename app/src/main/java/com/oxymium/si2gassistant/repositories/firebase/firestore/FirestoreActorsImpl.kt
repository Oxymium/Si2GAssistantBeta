package com.oxymium.si2gassistant.repositories.firebase.firestore

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.oxymium.si2gassistant.model.Actor
import com.oxymium.si2gassistant.model.State
import com.oxymium.si2gassistant.repositories.ActorsRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class FirestoreActorsImpl(val firebaseFirestore: FirebaseFirestore): ActorsRepository {

    override suspend fun getAllActors() = flow<State<List<Actor>>> {

        // LOADING
        emit(State.loading())

        firebaseFirestore
            .collection(ACTORS)
            .get()
            .await()
            .toObjects(Actor::class.java)
            .run { emit(State.success(this)) }

    }.catch {
        // FAILURE
        emit(State.failed(it.message.toString()))
    }

    override suspend fun getActorsByAcademyId(academyId: String?) = flow<State<List<Actor>>>{

        // LOADING
        emit(State.loading())

        firebaseFirestore
            .collection(ACTORS)
            .whereEqualTo("academy", academyId)
            .get()
            .await()
            .toObjects(Actor::class.java)
            .run { emit(State.success(this)) }

    }.catch {
    // FAILURE
    emit(State.failed(it.message.toString()))
}

    override suspend fun getActorById(actorId: String) = flow<State<Actor?>> {

        // LOADING
        emit(State.loading())

        firebaseFirestore
            .collection(ACTORS)
            .document(actorId)
            .get()
            .await()
            .toObject(Actor::class.java)
            .run { emit(State.success(this)) }

    }.catch{
        //FAILURE
        emit(State.failed(it.message.toString()))
    }

    override suspend fun createActor(actor: Actor) = flow<State<DocumentReference>> {

        // UPLOADING
        emit(State.loading())

        firebaseFirestore
            .collection(ACTORS)
            .add(actor)
            .await()
            .run { emit(State.success(this)) }

    }.catch{
        // FAILURE
        emit(State.failed(it.message.toString()))

    }

    override suspend fun addValidatedActorModule(actorId: String, moduleId: String) = flow<State<Void>> {

        // UPDATING
        emit(State.loading())

        firebaseFirestore
            .collection(ACTORS)
            .document(actorId)
            .update("validatedModules", FieldValue.arrayUnion(moduleId))
            .await()
            .run { emit(State.success(this))}

    }.catch{
        // FAILURE
        emit(State.failed(it.message.toString()))
    }

    override suspend fun removeValidatedActorModules(actorId: String, moduleId: String) = flow<State<Void>> {
        // UPDATING
        emit(State.loading())

        firebaseFirestore
            .collection(ACTORS)
            .document(actorId)
            .update("validatedModules", FieldValue.arrayRemove(moduleId))
            .await()
            .run { emit(State.success(this))}

    }.catch{
        // FAILURE
        emit(State.failed(it.message.toString()))
    }

}