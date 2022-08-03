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

        // Observers
        observeNavigationState()

        observeSelectedAcademy()
        observeSelectedActor()
        observeSelectedIssue()
    }

    // ---------
    // OBSERVERS
    // ---------

    private fun observeNavigationState(){
        navigationViewModel.navigationState.observe(this){
            when (it){
                // Login
                0 -> { navigateToLoginFragment() }
                // Normal User
                1 -> { navigateToNormalUserNavigation() }
                // Super User
                2 -> { navigateToSuperUserNavigation() }
            }
        }
    }

    // Handles navigation from Academies -> Actors
    private fun observeSelectedAcademy(){
        navigationViewModel.selectedAcademy.observe(this){
            if (it != null) {
                navigateToActorsFragment()
            }
        }
    }

    // Handles navigation from Actors -> Modules
    private fun observeSelectedActor(){
        navigationViewModel.selectedActor.observe(this){
            if (it != null) {
                navigateToModulesFragment()
            }
        }
    }

    // Handles navigation from Issues -> IssueDetails
    private fun observeSelectedIssue(){
        navigationViewModel.selectedIssue.observe(this){
            if (it != null){
                navigateToIssueDetailsFragment()
            }
        }
    }



    // ----------
    // NAVIGATION
    // ----------

    // Navigation Component
    private fun initNavigationUI(){
        bottomNavigationView = binding.bottomNavigation
        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.navController)
    }

    // ----------
    // NAVIGATION
    // ----------

    private fun navigateToNormalUserNavigation() = navHostFragment.navController.navigate(R.id.action_loginFragment_to_navigation_graph_normal_user)
    private fun navigateToSuperUserNavigation() = navHostFragment.navController.navigate(R.id.action_loginFragment_to_navigation_graph_super_user)
    private fun navigateToLoginFragment() = navHostFragment.navController.popBackStack(R.id.loginFragment, false)
    private fun navigateToIssueDetailsFragment() = navHostFragment.navController.navigate(R.id.action_issuesFragment_to_issueFragment)

    private fun navigateToActorsFragment() = navHostFragment.navController.navigate(R.id.action_academiesFragment_to_actorsFragment)
    private fun navigateToModulesFragment() = navHostFragment.navController.navigate(R.id.action_actorsFragment_to_modulesFragment)


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