package com.oxymium.si2gassistant.navigation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// ---------------------
// NAVIGATION VIEW MODEL
// ---------------------

class NavigationViewModel: ViewModel() {

    // ----------------
    // NAVIGATION STATE
    // ----------------

    /*
    0 = Login
    1 = Normal User
    2 = Super User
    */

    val navigationState = MutableLiveData(0)
}