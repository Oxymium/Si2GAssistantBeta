package com.oxymium.si2gassistant.features.issue

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.oxymium.si2gassistant.R
import com.oxymium.si2gassistant.databinding.FragmentIssueBinding
import com.oxymium.si2gassistant.databinding.FragmentIssuesBinding
import com.oxymium.si2gassistant.features.issues.IssuesViewModel
import com.oxymium.si2gassistant.navigation.NavigationViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

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

    private val issueViewModel by viewModel<IssueViewModel>()

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

        return binding.root

    }

}