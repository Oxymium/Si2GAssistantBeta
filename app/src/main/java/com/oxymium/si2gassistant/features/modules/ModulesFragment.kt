package com.oxymium.si2gassistant.features.modules

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.oxymium.si2gassistant.R
import com.oxymium.si2gassistant.databinding.FragmentModulesBinding
import com.oxymium.si2gassistant.navigation.NavigationViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

// ----------------
// MODULES FRAGMENT
// ----------------

class ModulesFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var fragmentModulesBinding: FragmentModulesBinding
    private val binding get() = fragmentModulesBinding

    // NavigationViewModel
    private val navigationViewModel by sharedViewModel<NavigationViewModel>()

    private val modulesBinding by viewModel<ModulesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(fragmentTAG, "onCreate: ")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fragmentModulesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_modules, container, false)
        fragmentModulesBinding.lifecycleOwner = activity
        fragmentModulesBinding.navigationViewModel = navigationViewModel

        return binding.root

    }

}