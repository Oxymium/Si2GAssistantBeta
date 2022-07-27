package com.oxymium.si2gassistant.repositories.firebase.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import com.oxymium.si2gassistant.model.State
import com.oxymium.si2gassistant.model.User
import com.oxymium.si2gassistant.repositories.UserRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class FirestoreUserImpl(val firebaseFirestore: FirebaseFirestore): UserRepository {

    override suspend fun getUserWithGivenUid(uid: String) = flow<State<User?>>{

        // LOADING
        emit(State.loading())

        firebaseFirestore
            .collection(USERS)
            .document(uid)
            .get()
            .await()
            .toObject(User::class.java)
            .run { emit(State.success(this)) }

    }.catch {
        // FAILURE
        emit(State.failed(it.message.toString()))
    }

}