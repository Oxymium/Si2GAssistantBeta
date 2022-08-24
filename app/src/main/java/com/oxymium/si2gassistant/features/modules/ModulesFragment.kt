package com.oxymium.si2gassistant.features.modules

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
import com.oxymium.si2gassistant.databinding.FragmentModulesBinding
import com.oxymium.si2gassistant.model.Module
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
        modulesAdapter = ModulesAdapter(
            navigationViewModel.currentUser.value?.superUser ?: false,
            ModuleListener{
                Log.d("Item module:", "$it")
                modulesViewModel.moduleToUpdate.value = it
            }
        )

        // Observe issue list
        modulesViewModel.finalModules.observe(viewLifecycleOwner) {
                modules -> modulesAdapter.submitList(modules)
        }

        // Attach adapter to recyclerView
        fragmentModulesBinding.fragmentModulesRecyclerView.adapter = modulesAdapter

        navigationViewModel.selectedActor.value

        observeActorValidatedModules()
        observeValidatedModules()
        observeClickedModuleId()

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
        modulesViewModel.allModules.observe(viewLifecycleOwner){
            if (it != null){
                modulesViewModel.updateAllModulesWithValidatedModules()
            }
        }
    }

    private fun observeClickedModuleId(){
        modulesViewModel.moduleToUpdate.observe(viewLifecycleOwner){
            if (it != null){
                displayUpdateModuleDialog(it)
            }
        }
    }

    private fun displayUpdateModuleDialog(module: Module) {

        when (module.validated){
            true -> {

                val negativeButtonClick = { _: DialogInterface, _: Int -> }
                val positiveButtonClick = { _: DialogInterface, _: Int ->
                    // Update module here
                    modulesViewModel.removeActorValidatedModule(navigationViewModel.selectedActor.value?.id, module.id)
                }

                val builder = AlertDialog.Builder(requireActivity(), R.style.AlertDialogStyle)
                with(builder)
                {
                    setTitle(String.format(getString(R.string.alert_module_update_title), module.id))
                    setMessage(R.string.alert_module_update_uncheck_message)
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
            false -> {

                val negativeButtonClick = { _: DialogInterface, _: Int -> }
                val positiveButtonClick = { _: DialogInterface, _: Int ->
                    // Update module here
                    modulesViewModel.addActorValidatedModule(navigationViewModel.selectedActor.value?.id, module.id)
                }

                val builder = AlertDialog.Builder(requireActivity(), R.style.AlertDialogStyle)
                with(builder)
                {
                    setTitle(String.format(getString(R.string.alert_module_update_title), module.id))
                    setMessage(R.string.alert_module_update_check_message)
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
            else -> {}
        }

    }
}