package com.oxymium.si2gassistant.features.issue

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.oxymium.si2gassistant.R
import com.oxymium.si2gassistant.databinding.FragmentIssueBinding
import com.oxymium.si2gassistant.navigation.NavigationViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

// ---------------
// ISSUES FRAGMENT
// ---------------

class IssueFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var fragmentIssueBinding: FragmentIssueBinding
    private val binding get() = fragmentIssueBinding

    // NavigationViewModel
    private val navigationViewModel by sharedViewModel<NavigationViewModel>()

    // IssueViewModel
    private val issueViewModel by sharedViewModel<IssueViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(fragmentTAG, "onCreate: ")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fragmentIssueBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_issue, container, false)
        fragmentIssueBinding.lifecycleOwner = activity
        fragmentIssueBinding.navigationViewModel = navigationViewModel

        fragmentIssueBinding.fragmentIssueResultsInclude.navigationViewModel = navigationViewModel
        fragmentIssueBinding.fragmentIssueContentInclude.navigationViewModel = navigationViewModel
        fragmentIssueBinding.fragmentIssueContentInclude.issueViewModel = issueViewModel
        fragmentIssueBinding.fragmentIssueUpdateInclude.issueViewModel = issueViewModel

        // Fetch Issue
        navigationViewModel.selectedIssueId.value?.let { issueViewModel.getIssueById(it) }

        observeDisplayDialogState()

        return binding.root

    }

    private fun observeDisplayDialogState(){
        issueViewModel.issueUpdateClicked.observe(viewLifecycleOwner){
            if (it){
                displayUpdateIssueDialog()
            }
        }
    }

    private fun displayUpdateIssueDialog() {

        val negativeButtonClick = { _: DialogInterface, _: Int ->
            issueViewModel.issueUpdateClicked.value = false }
        val positiveButtonClick = { _: DialogInterface, _: Int ->
            issueViewModel.issueUpdateClicked.value = false
            issueViewModel.issue.value?.id?.let { issueViewModel.onClickUpdateIssue(it) }
            Unit
        }

        val builder = AlertDialog.Builder(requireActivity(), R.style.AlertDialogStyle)
        with(builder)
        {
            setTitle(R.string.alert_report_issue_update_title)
            setMessage(R.string.alert_report_issue_update_message)
            setNegativeButton(
                R.string.alert_negative,
                DialogInterface.OnClickListener(function = negativeButtonClick)
            )
            setPositiveButton(
                R.string.alert_positive,
                DialogInterface.OnClickListener(function = positiveButtonClick)
            )
            show()
        }
    }

}