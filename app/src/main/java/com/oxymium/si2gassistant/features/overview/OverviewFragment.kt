package com.oxymium.si2gassistant.features.overview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.oxymium.si2gassistant.R
import com.oxymium.si2gassistant.databinding.FragmentOverviewBinding
import com.oxymium.si2gassistant.navigation.NavigationViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

// -----------------
// OVERVIEW FRAGMENT
// -----------------

class OverviewFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var fragmentOverviewBinding: FragmentOverviewBinding
    private val binding get() = fragmentOverviewBinding

    // NavigationViewModel
    private val navigationViewModel by sharedViewModel<NavigationViewModel>()

    private val overviewViewModel by viewModel<OverviewViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(fragmentTAG, "onCreate: ")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fragmentOverviewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_overview, container, false)
        fragmentOverviewBinding.lifecycleOwner = activity
        fragmentOverviewBinding.navigationViewModel = navigationViewModel
        fragmentOverviewBinding.overviewViewModel = overviewViewModel

        return binding.root

    }

}