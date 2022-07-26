package com.oxymium.si2gassistant.di

import com.oxymium.si2gassistant.features.academies.AcademiesViewModel
import com.oxymium.si2gassistant.features.actors.ActorsViewModel
import com.oxymium.si2gassistant.features.addactor.AddActorViewModel
import com.oxymium.si2gassistant.features.issue.IssueViewModel
import com.oxymium.si2gassistant.features.issues.IssuesViewModel
import com.oxymium.si2gassistant.features.modules.ModulesViewModel
import com.oxymium.si2gassistant.features.overview.OverviewViewModel
import com.oxymium.si2gassistant.features.reportissue.ReportIssueViewModel
import com.oxymium.si2gassistant.features.userlogin.UserLoginViewModel
import com.oxymium.si2gassistant.navigation.NavigationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

// -------------------------
// APP MODULE (KOIN MODULES)
// -------------------------

// Local Firebase emulator, set this to TRUE for live version
const val EMULATOR = true

val appModules = module {

    // ViewModels
    viewModel { NavigationViewModel() }

    viewModel { AcademiesViewModel() }
    viewModel { ActorsViewModel() }
    viewModel { AddActorViewModel() }
    viewModel { IssueViewModel() }
    viewModel { IssuesViewModel() }
    viewModel { ModulesViewModel() }
    viewModel { OverviewViewModel() }
    viewModel { ReportIssueViewModel() }
    viewModel { UserLoginViewModel() }

}
