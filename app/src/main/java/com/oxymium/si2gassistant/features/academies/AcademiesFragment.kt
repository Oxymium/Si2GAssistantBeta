package com.oxymium.si2gassistant.features.academies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.oxymium.si2gassistant.R
import com.oxymium.si2gassistant.databinding.FragmentAcademiesBinding
import com.oxymium.si2gassistant.navigation.NavigationViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

// ------------------
// ACADEMIES FRAGMENT
// ------------------

class AcademiesFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var fragmentAcademiesBinding: FragmentAcademiesBinding
    private val binding get() = fragmentAcademiesBinding

    // NavigationViewModel
    private val navigationViewModel by sharedViewModel<NavigationViewModel>()

    private val academiesViewModel by viewModel<AcademiesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(fragmentTAG, "onCreate: ")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fragmentAcademiesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_academies, container, false)
        fragmentAcademiesBinding.lifecycleOwner = activity
        fragmentAcademiesBinding.navigationViewModel = navigationViewModel

        return binding.root

    }

}