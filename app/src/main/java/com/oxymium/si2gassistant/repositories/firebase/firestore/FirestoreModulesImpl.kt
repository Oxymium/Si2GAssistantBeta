package com.oxymium.si2gassistant.repositories.firebase.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.oxymium.si2gassistant.model.Module
import com.oxymium.si2gassistant.model.State
import com.oxymium.si2gassistant.repositories.ModulesRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class FirestoreModulesImpl(val firebaseFirestore: FirebaseFirestore): ModulesRepository {

    override suspend fun getAllModules() = flow<State<List<Module>>> {

        // LOADING
        emit(State.loading())

        firebaseFirestore
            .collection(MODULES)
            .get()
            .await()
            .toObjects(Module::class.java)
            .run { emit(State.success(this)) }

    }.catch {
        // FAILURE
        emit(State.failed(it.message.toString()))
    }
}