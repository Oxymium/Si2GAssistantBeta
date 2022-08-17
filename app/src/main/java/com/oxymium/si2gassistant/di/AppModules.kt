package com.oxymium.si2gassistant.di

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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
import com.oxymium.si2gassistant.repositories.*
import com.oxymium.si2gassistant.repositories.firebase.auth.AuthImpl
import com.oxymium.si2gassistant.repositories.firebase.firestore.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

// -------------------------
// APP MODULE (KOIN MODULES)
// -------------------------

// Local Firebase emulator, set this to FALSE for live version
const val EMULATOR = true

val appModules = module {

    // ViewModels
    viewModel { NavigationViewModel() }

    viewModel { AcademiesViewModel( get() ) }
    viewModel { ActorsViewModel( get() ) }
    viewModel { AddActorViewModel( get() ) }
    viewModel { IssueViewModel( get() ) }
    viewModel { IssuesViewModel( get() ) }
    viewModel { ModulesViewModel( get() ) }
    viewModel { OverviewViewModel( get(), get(), get()) }
    viewModel { ReportIssueViewModel( get() ) }
    viewModel { UserLoginViewModel( get(), get(), get() ) }

    // Firebase AUTH singleton
    single { Firebase.auth.apply {
        if (EMULATOR) { useEmulator("10.0.2.2", 9099)}
        else { //FirebaseAuth.getInstance() }}
        }}}

    // Firebase FIRESTORE singleton
    single { Firebase.firestore.apply {
        if (EMULATOR) { useEmulator("10.0.2.2", 8080)}
        else { //FirebaseFirestore.getInstance() }}
        }}}

    // Singletons
    single<AuthRepository> { AuthImpl(firebaseAuth = get() ) }

    single<IssuesRepository> { FirestoreIssuesImpl(firebaseFirestore = get() ) }
    single<UserRepository> { FirestoreUserImpl(firebaseFirestore = get() ) }
    single<AcademiesRepository> { FirestoreAcademiesImpl(firebaseFirestore = get() ) }
    single<ActorsRepository> { FirestoreActorsImpl(firebaseFirestore = get() ) }
    single<ModulesRepository> { FirestoreModulesImpl(firebaseFirestore = get() ) }

}
