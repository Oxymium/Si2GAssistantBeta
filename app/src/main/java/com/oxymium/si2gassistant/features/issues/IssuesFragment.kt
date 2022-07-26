package com.oxymium.si2gassistant.features.issues

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.oxymium.si2gassistant.R
import com.oxymium.si2gassistant.databinding.FragmentIssuesBinding
import com.oxymium.si2gassistant.navigation.NavigationViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

// ---------------
// ISSUES FRAGMENT
// ---------------

class IssuesFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var fragmentIssuesBinding: FragmentIssuesBinding
    private val binding get() = fragmentIssuesBinding

    // NavigationViewModel
    private val navigationViewModel by sharedViewModel<NavigationViewModel>()

    private val issuesViewModel by viewModel<IssuesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(fragmentTAG, "onCreate: ")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fragmentIssuesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_issues, container, false)
        fragmentIssuesBinding.lifecycleOwner = activity
        fragmentIssuesBinding.navigationViewModel = navigationViewModel

        return binding.root

    }

}