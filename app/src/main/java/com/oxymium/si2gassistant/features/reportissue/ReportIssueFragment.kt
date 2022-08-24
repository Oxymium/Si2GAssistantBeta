package com.oxymium.si2gassistant.features.reportissue

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.drawable.Animatable2
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.oxymium.si2gassistant.R
import com.oxymium.si2gassistant.databinding.DialogReportIssueBinding
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

        // Bind header, body & upload parts
        fragmentReportIssueBinding.fragmentReportIssueHeaderInclude.reportIssueViewModel = reportIssueViewModel
        fragmentReportIssueBinding.fragmentReportIssueHeaderInclude.navigationViewModel = navigationViewModel
        fragmentReportIssueBinding.fragmentReportIssueBodyInclude.reportIssueViewModel = reportIssueViewModel

        fragmentReportIssueBinding.fragmentReportIssueUploadInclude.reportIssueViewModel = reportIssueViewModel



        // Copy current Academy
        reportIssueViewModel.academy.value = navigationViewModel.currentAcademy.value

        // Sphere animation
        val animatedSphere = fragmentReportIssueBinding.fragmentReportIssueUploadInclude.layoutReportIssueCircle.drawable as AnimatedVectorDrawable
        animatedSphere.start()
        animatedSphere.registerAnimationCallback(
            object : Animatable2.AnimationCallback(){
                override fun onAnimationEnd(drawable: Drawable?) {
                    fragmentReportIssueBinding.fragmentReportIssueUploadInclude.layoutReportIssueCircle.post{ (animatedSphere).start() }
                }
            }
        )

        observeNotificationTrigger()
        observeReportIssueButtonState()
        observeCategoryFieldState()

        return binding.root

        }


    // ---------
    // OBSERVERS
    // ---------

    private fun observeNotificationTrigger(){
        reportIssueViewModel.triggerCreatedIssueNotification.observe(viewLifecycleOwner){
            if (it){
                view?.let { view -> Snackbar.make(view, "Issue successfully pushed", Snackbar.LENGTH_LONG).show() }
            }
        }
    }

    private fun observeReportIssueButtonState(){
        reportIssueViewModel.reportIssueButtonClicked.observe(viewLifecycleOwner){
            if (it){
                reportIssueDialog()
            }
        }
    }

    private fun observeCategoryFieldState(){
        reportIssueViewModel.categoryFieldClickedState.observe(viewLifecycleOwner){
            if (it){
                displayCategoryDialog()
            }
        }
    }

    // --------------
    // DIALOG WINDOWS
    // --------------

    private fun displayCategoryDialog() {

        // Fetch categories array from res
        val categories = resources.getStringArray(R.array.categories_array)

        val neutralButtonClick = { _: DialogInterface, _: Int -> reportIssueViewModel.categoryFieldClickedState.value = false }
        val builder = AlertDialog.Builder(requireActivity(), R.style.AlertDialogStyle)

        with(builder)
        {
            setTitle(R.string.alert_category_title)
            setSingleChoiceItems(categories, -1){ _, which ->
                reportIssueViewModel.category.value = categories[which]
            }
            setNeutralButton(
                R.string.alert_neutral,
                DialogInterface.OnClickListener(function = neutralButtonClick)
            )
            show()
        }
    }

    private fun reportIssueDialog() {

        val negativeButtonClick = { _: DialogInterface, _: Int ->
            // Reset state back to false
            reportIssueViewModel.reportIssueButtonClicked.value = false }
        val positiveButtonClick = { _: DialogInterface, _: Int ->
            reportIssueViewModel.reportIssueButtonClicked.value = false
            reportIssueViewModel.pushIssue()
        }

        val builder = AlertDialog.Builder(requireActivity(), R.style.AlertDialogStyle)
        val alertBinding: DialogReportIssueBinding = DataBindingUtil.inflate(this.layoutInflater, R.layout.dialog_report_issue, null, false)
        alertBinding.lifecycleOwner = this
        alertBinding.reportIssueViewModel = reportIssueViewModel

        with(builder)
        {
            setTitle(R.string.alert_report_issue_title)
            setNegativeButton(
                R.string.alert_negative,
                DialogInterface.OnClickListener(function = negativeButtonClick)
            )
            setPositiveButton(
                R.string.alert_positive,
                DialogInterface.OnClickListener(function = positiveButtonClick)
            )

            setView(alertBinding.root)
            show()
        }
    }

}