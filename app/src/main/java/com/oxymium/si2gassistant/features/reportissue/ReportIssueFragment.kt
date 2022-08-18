package com.oxymium.si2gassistant.features.reportissue

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.oxymium.si2gassistant.R
import com.oxymium.si2gassistant.databinding.FragmentReportIssueBinding
import com.oxymium.si2gassistant.navigation.NavigationViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

// ---------------------
// REPORT ISSUE FRAGMENT
// ---------------------

class ReportIssueFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var fragmentReportIssueBinding: FragmentReportIssueBinding
    private val binding get() = fragmentReportIssueBinding

    // NavigationViewModel
    private val navigationViewModel by sharedViewModel<NavigationViewModel>()

    private val reportIssueViewModel by viewModel<ReportIssueViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(fragmentTAG, "onCreate: ")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fragmentReportIssueBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_report_issue, container, false)
        fragmentReportIssueBinding.lifecycleOwner = activity

        fragmentReportIssueBinding.reportIssueViewModel = reportIssueViewModel
        fragmentReportIssueBinding.navigationViewModel = navigationViewModel
        fragmentReportIssueBinding.fragmentReportIssueHeaderInclude.reportIssueViewModel = reportIssueViewModel

        // Copy current Academy
        reportIssueViewModel.academy.value = navigationViewModel.currentAcademy.value

        observeNotificationTrigger()

        return binding.root

        }

    private fun observeNotificationTrigger(){
        reportIssueViewModel.triggerCreatedIssueNotification.observe(viewLifecycleOwner){
            if (it){
                view?.let { view -> Snackbar.make(view, "Issue successfully pushed", Snackbar.LENGTH_LONG).show() }
            }
        }
    }


}