package com.oxymium.si2gassistant.features.issue

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.oxymium.si2gassistant.R
import com.oxymium.si2gassistant.databinding.FragmentIssueBinding
import com.oxymium.si2gassistant.navigation.NavigationViewModel
import kotlinx.coroutines.launch
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
        fragmentIssueBinding.fragmentIssueUpdateInclude.issueViewModel = issueViewModel

        observeUpdateIssueStatus()

        return binding.root

    }

    private fun observeUpdateIssueStatus(){
        issueViewModel.issueUpdateClicked.observe(viewLifecycleOwner){
            if (it){
                lifecycleScope.launch { navigationViewModel.selectedIssue.value?.id?.let { it1 ->
                    issueViewModel.onClickUpdateIssue(
                        it1
                    )
                } }
            }
        }
    }

}