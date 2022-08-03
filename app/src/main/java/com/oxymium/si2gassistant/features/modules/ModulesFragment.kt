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
import com.oxymium.si2gassistant.utils.CustomLayoutManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

// ----------------
// MODULES FRAGMENT
// ----------------

class ModulesFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // RecyclerView Adapter
    private lateinit var modulesAdapter: ModulesAdapter

    // DataBinding
    private lateinit var fragmentModulesBinding: FragmentModulesBinding
    private val binding get() = fragmentModulesBinding

    // NavigationViewModel
    private val navigationViewModel by sharedViewModel<NavigationViewModel>()

    private val modulesViewModel by viewModel<ModulesViewModel>()

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

        // Bind viewModels to module results
        fragmentModulesBinding.fragmentModulesResultsInclude.modulesViewModel = modulesViewModel
        fragmentModulesBinding.fragmentModulesResultsInclude.navigationViewModel = navigationViewModel

        fragmentModulesBinding.fragmentModulesRecyclerView.layoutManager = CustomLayoutManager(context)

        // Initialize adapter
        modulesAdapter = ModulesAdapter()

        // Observe issue list
        modulesViewModel.allModules.observe(viewLifecycleOwner) {
                modules -> modulesAdapter.submitList(modules)
        }

        // Attach adapter to recyclerView
        fragmentModulesBinding.fragmentModulesRecyclerView.adapter = modulesAdapter

        navigationViewModel.selectedActor.value

        observeActorValidatedModules()
        observeValidatedModules()

        return binding.root

    }

    // Observe Actor's validatedModules
    private fun observeActorValidatedModules(){
        navigationViewModel.selectedActor.observe(viewLifecycleOwner){
            if (it != null){
                Log.d("Modules: validated modules:", "${it.validatedModules}")
                modulesViewModel.validatedModules.value = it.validatedModules
            }
        }
    }

    private fun observeValidatedModules(){
        modulesViewModel.validatedModules.observe(viewLifecycleOwner){
            if (it != null){
                modulesViewModel.updateAllModulesWithValidatedModules()
            }
        }
    }
}