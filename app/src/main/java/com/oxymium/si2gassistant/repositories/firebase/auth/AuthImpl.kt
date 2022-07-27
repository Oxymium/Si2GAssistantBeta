package com.oxymium.si2gassistant.repositories.firebase.auth

import com.google.firebase.auth.FirebaseAuth
import com.oxymium.si2gassistant.model.State
import com.oxymium.si2gassistant.repositories.AuthRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

// --------------------------
// AuthFirebaseUserRepository
// --------------------------

class AuthImpl(val firebaseAuth: FirebaseAuth): AuthRepository {

    override suspend fun getFirebaseUser(mail: String, password: String) = flow {

        // LOADING
        emit(State.loading())

        firebaseAuth
            .signInWithEmailAndPassword(mail, password)
            .await()
            .run { emit(State.success(this.user)) }

    }.catch {
        // FAILURE
        emit(State.failed(it.message.toString()))
    }

}

