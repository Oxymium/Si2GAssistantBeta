package com.oxymium.si2gassistant.repositories

import com.google.firebase.auth.FirebaseUser
import com.oxymium.si2gassistant.model.State
import com.oxymium.si2gassistant.model.User
import kotlinx.coroutines.flow.Flow

// --------------
// UserRepository
// --------------

interface AuthRepository {

    // GET
    suspend fun getFirebaseUser(mail: String, password: String): Flow<State<FirebaseUser?>>

}