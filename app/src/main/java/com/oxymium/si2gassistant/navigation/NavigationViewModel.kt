package com.oxymium.si2gassistant.navigation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oxymium.si2gassistant.model.Academy
import com.oxymium.si2gassistant.model.Actor
import com.oxymium.si2gassistant.model.Issue
import com.oxymium.si2gassistant.model.User
import java.util.*

// ---------------------
// NAVIGATION VIEW MODEL
// ---------------------

class NavigationViewModel: ViewModel() {

    // --------------------
    // SHARED FRAGMENT DATA
    // --------------------

    val currentDate = MutableLiveData<Long?>(Calendar.getInstance().timeInMillis)

    val currentUser = MutableLiveData<User?>(null)

    val currentAcademy = MutableLiveData<Academy?>(null)

    val selectedAcademy = MutableLiveData<Academy?>(null)

    val selectedIssue = MutableLiveData<Issue?>(null)

    val selectedActor = MutableLiveData<Actor?>(null)

    // -------------------------------------
    // NULLIFY VALUES BACK TO NULL ON LOGOUT
    // -------------------------------------

    fun nullifySharedDataValues(){
        currentUser.value = null
        currentAcademy.value = null
        currentAcademy.value = null
    }

    // ----------------
    // NAVIGATION STATE
    // ----------------

    /*
    0 = Login
    1 = Normal User
    2 = Super User
    */

    val navigationState = MutableLiveData(0)

    // -------------
    // BUTTONS LOGIC
    // -------------

    // Login button
    val loginButtonClicked: MutableLiveData<Boolean> = MutableLiveData(false)

    fun onClickLoginButton() { loginButtonClicked.value = true }

    // Logout button
    val logoutButtonClicked: MutableLiveData<Boolean> = MutableLiveData(false)

    fun onClickLogoutButton() { logoutButtonClicked.value = true }

}