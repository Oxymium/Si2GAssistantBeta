package com.oxymium.si2gassistant.repositories.firebase.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.oxymium.si2gassistant.model.Academy
import com.oxymium.si2gassistant.model.State
import com.oxymium.si2gassistant.repositories.AcademiesRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class FirestoreAcademiesImpl(val firebaseFirestore: FirebaseFirestore): AcademiesRepository {

    override suspend fun getAcademyWithGivenUserId(id: String) = flow<State<Academy?>>{

        // LOADING
        emit(State.loading())

        firebaseFirestore
            .collection(ACADEMIES)
            .document(id)
            .get()
            .await()
            .toObject(Academy::class.java)
            .run { emit(State.success(this)) }

    }.catch {
        // FAILURE
        emit(State.failed(it.message.toString()))
    }

}