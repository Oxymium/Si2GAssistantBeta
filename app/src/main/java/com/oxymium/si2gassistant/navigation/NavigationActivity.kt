package com.oxymium.si2gassistant.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.oxymium.si2gassistant.R

// --------------------
// NAVIGATION ACTIVITY
// --------------------

class NavigationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Misc.
        disableWindowAutoResizingWhenKeyboardCalled()
        hideSupportActionBar()
    }

    // --------------
    // MISC. ELEMENTS
    // --------------

    // Prevents window resizing when virtual keyboard opens
    private fun disableWindowAutoResizingWhenKeyboardCalled() {
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
    }

    // Hide top bar
    private fun hideSupportActionBar() {
        supportActionBar?.hide()
    }

}