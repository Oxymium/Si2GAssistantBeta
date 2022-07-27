package com.oxymium.si2gassistant.repositories

import com.google.firebase.auth.FirebaseUser
import com.oxymium.si2gassistant.model.State
import com.oxymium.si2gassistant.model.User
import kotlinx.coroutines.flow.Flow

// ----------------------
// FirebaseUserRepository
// ----------------------

interface UserRepository {

    // GET
    suspend fun getUserWithGivenUid(uid: String): Flow<State<User?>>
}