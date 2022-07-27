package com.oxymium.si2gassistant.features.userlogin

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.oxymium.si2gassistant.model.Academy
import com.oxymium.si2gassistant.model.State
import com.oxymium.si2gassistant.model.User
import com.oxymium.si2gassistant.repositories.AcademiesRepository
import com.oxymium.si2gassistant.repositories.AuthRepository
import com.oxymium.si2gassistant.repositories.UserRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

// ---------------------
// USER LOGIN VIEW MODEL
// ---------------------

class UserLoginViewModel(val authRepository: AuthRepository, val userRepository: UserRepository, val academiesRepository: AcademiesRepository): ViewModel() {


    // Mail & password for login
    val mail = MutableLiveData<String>(null)
    val password = MutableLiveData<String>(null)

    // Placeholders for testing purposes
    fun loadNormalUser(){
        mail.value = "normal_user@gmail.test"
        password.value = "xxxyyy"
    }

    fun loadSuperUser(){
        mail.value = "super_user@gmail.test"
        password.value = "xxxyyy"
    }

    // Snackbar error message
    val snackBarErrorMessage = MutableLiveData<String?>(null)

    // GET FIREBASE USER (AUTH)
    val auth = MutableLiveData<FirebaseUser?>()

    fun getAuthUid(mail: String?, password: String?) = viewModelScope.launch {
        if (mail != null) {
            if (password != null) {
                authRepository.getFirebaseUser(mail, password).collect {
                    when (it) {
                        is State.Loading -> Log.d("Auth:", "Loading...")
                        is State.Success -> {
                            Log.d("Auth:", "Success!")
                            Log.d( "Auth:", "${it.data}")
                            auth.value = it.data }
                        is State.Failed -> {
                            Log.d("Auth:", "Failed!")
                        }
                    }
                }
            }
        }
    }

    // GET FIRESTORE USER
    val user = MutableLiveData<User?>()

    fun getFirestoreUser(uid: String) = viewModelScope.launch {
        userRepository.getUserWithGivenUid(uid).collect {
            when (it) {
                is State.Loading -> Log.d("User:", "Loading...")
                is State.Success -> {
                    Log.d("User:", "Success!")
                    Log.d( "User:", "${it.data}")
                    user.value = it.data }
                is State.Failed -> {
                    Log.d("User:", "Failed!")
                    snackBarErrorMessage.value = it.message
                    println(it.message)
                }
            }
        }
    }

    // GET FIRESTORE ACADEMY
    val academy = MutableLiveData<Academy?>()

    fun getFirestoreAcademy(id: String) = viewModelScope.launch {
        academiesRepository.getAcademyWithGivenUserId(id).collect {
            when (it) {
                is State.Loading -> Log.d("Academy:", "Loading...")
                is State.Success -> {
                    Log.d("Academy:", "Success!")
                    Log.d( "Academy:", "${it.data}")
                    academy.value = it.data
                }
                is State.Failed -> Log.d("Academy:", "Failed!")
            }
        }
    }

    fun nullifyUserLoginValues(){
        auth.value = null
        user.value = null
        academy.value = null
    }


}