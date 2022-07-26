package com.oxymium.si2gassistant.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.oxymium.si2gassistant.R
import com.oxymium.si2gassistant.databinding.ActivityNavigationBinding
import com.oxymium.si2gassistant.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.GlobalContext.startKoin

// --------------------
// NAVIGATION ACTIVITY
// --------------------

class NavigationActivity : AppCompatActivity() {

    // DataBinding
    private lateinit var activityNavigationBinding: ActivityNavigationBinding
    private val binding get() = activityNavigationBinding

    // Navigation
    private val navHostFragment get() = supportFragmentManager.findFragmentById(R.id.navigationHostFragment) as NavHostFragment
    private lateinit var bottomNavigationView: BottomNavigationView

    // NavigationViewModel
    private val navigationViewModel by viewModel<NavigationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // KOIN INJECTION
        startKoin {
            androidContext(androidContext = this@NavigationActivity)
            modules(appModules)
        }

        activityNavigationBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_navigation, findViewById(android.R.id.content), false)
        activityNavigationBinding.lifecycleOwner = this
        activityNavigationBinding.navigationViewModel = navigationViewModel

        binding.root.doOnPreDraw { initNavigationUI() }
        setContentView(binding.root)

        // Misc.
        disableWindowAutoResizingWhenKeyboardCalled()
        hideSupportActionBar()
    }

    // ----------
    // NAVIGATION
    // ----------

    // Navigation Component
    private fun initNavigationUI(){
        bottomNavigationView = binding.bottomNavigation
        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.navController)
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