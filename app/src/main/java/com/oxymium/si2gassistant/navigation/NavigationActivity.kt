package com.oxymium.si2gassistant.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
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

    private val options = NavOptions.Builder()
        .setLaunchSingleTop(true)
        .setEnterAnim(R.anim.slide_in)
        .setExitAnim(R.anim.fade_out)
        .setPopEnterAnim(R.anim.fade_in)
        .setPopExitAnim(R.anim.slide_out)
        .build()

    private val loginLogoutOptions = NavOptions.Builder()
        .setLaunchSingleTop(true)
        .setEnterAnim(R.anim.zoom_in)
        .setExitAnim(R.anim.zoom_out)
        .setPopEnterAnim(R.anim.zoom_in)
        .setPopExitAnim(R.anim.zoom_out)
        .build()

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

        bottomNavigationView = binding.bottomNavigation


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
        navigationViewModel.selectedIssueId.observe(this){
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

        bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                // Academies
                R.id.academiesFragment -> navHostFragment.navController.navigate(R.id.academiesFragment, null, options)
                // Overview
                R.id.overviewFragment -> navHostFragment.navController.navigate(R.id.overviewFragment, null, options)
                // Issues
                R.id.issuesFragment -> navHostFragment.navController.navigate(R.id.issuesFragment, null, options)
                // User
                R.id.userFragment -> navHostFragment.navController.navigate(R.id.userFragment, null, options)
                // ReportIssue
                R.id.reportIssueFragment -> navHostFragment.navController.navigate(R.id.reportIssueFragment, null, options)
                // AddActor
                R.id.addActorFragment -> navHostFragment.navController.navigate(R.id.addActorFragment, null, options)
                // Actors
                R.id.actorsFragment -> navHostFragment.navController.navigate(R.id.actorsFragment, null, options)
            }
            true
        }
    }

    // ----------
    // NAVIGATION
    // ----------

    // NU navigation
    private fun navigateToNormalUserNavigation() = navHostFragment.navController.navigate(R.id.action_loginFragment_to_navigation_graph_normal_user, null, loginLogoutOptions)
    // SU navigation
    private fun navigateToSuperUserNavigation() = navHostFragment.navController.navigate(R.id.action_loginFragment_to_navigation_graph_super_user, null, loginLogoutOptions)
    // User -> Login
    private fun navigateToLoginFragment() = navHostFragment.navController.popBackStack(R.id.loginFragment, false, )
    // Issues -> Issue
    private fun navigateToIssueDetailsFragment() = navHostFragment.navController.navigate(R.id.action_issuesFragment_to_issueFragment, null, options)
    // Academies -> Actors
    private fun navigateToActorsFragment() = navHostFragment.navController.navigate(R.id.action_academiesFragment_to_actorsFragment, null, options)
    // Actors -> Modules
    private fun navigateToModulesFragment() = navHostFragment.navController.navigate(R.id.action_actorsFragment_to_modulesFragment, null, options)

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