package com.oxymium.si2gassistant.features.addactor

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.oxymium.si2gassistant.R
import com.oxymium.si2gassistant.databinding.DialogAddActorBinding
import com.oxymium.si2gassistant.databinding.FragmentAddActorBinding
import com.oxymium.si2gassistant.model.Actor
import com.oxymium.si2gassistant.navigation.NavigationViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

// ------------------
// ADD ACTOR FRAGMENT
// ------------------

class AddActorFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var fragmentAddActorBinding: FragmentAddActorBinding
    private val binding get() = fragmentAddActorBinding

    // NavigationViewModel
    private val navigationViewModel by sharedViewModel<NavigationViewModel>()

    private val addActorViewModel by viewModel<AddActorViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(fragmentTAG, "onCreate: ")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fragmentAddActorBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_actor, container, false)
        fragmentAddActorBinding.lifecycleOwner = activity

        fragmentAddActorBinding.navigationViewModel = navigationViewModel
        fragmentAddActorBinding.addActorViewModel = addActorViewModel
        fragmentAddActorBinding.fragmentAddActorHeaderInclude.addActorViewModel = addActorViewModel

        observeAddActorButtonState()
        observeRoleFieldState()

        return binding.root

    }

    private fun observeRoleFieldState(){
        addActorViewModel.roleFieldClickedState.observe(viewLifecycleOwner){
            if (it){
                displayRoleDialog()
            }
        }
    }

    private fun observeAddActorButtonState(){
        addActorViewModel.addActorButtonClicked.observe(viewLifecycleOwner){
            if (it) {
                displayAddActorDialog()
            }
        }
    }

    private fun displayRoleDialog() {

        val roles = arrayOf("Assistant", "Commercial", "Directeur opérationnel")

        val neutralButtonClick = { _: DialogInterface, _: Int -> addActorViewModel.roleFieldClickedState.value = false }
        val builder = AlertDialog.Builder(requireActivity(), R.style.AlertDialogStyle)

        with(builder)
        {
            setTitle(R.string.alert_role_title)
            setSingleChoiceItems(roles, -1){ _, which ->
                addActorViewModel.role.value = roles[which]
            }
            setNeutralButton(
                R.string.alert_neutral,
                DialogInterface.OnClickListener(function = neutralButtonClick)
            )
            show()
        }
    }

    private fun displayAddActorDialog() {

        val negativeButtonClick = { _: DialogInterface, _: Int ->
            // Reset state back to false
            addActorViewModel.addActorButtonClicked.value = false }
        val positiveButtonClick = { _: DialogInterface, _: Int ->
            addActorViewModel.addActorButtonClicked.value = false
            // Push actor
            lifecycleScope.launch{
            addActorViewModel.createActor(Actor("", addActorViewModel.firstname.value, addActorViewModel.name.value, addActorViewModel.role.value, "", listOf()))
                }
            Unit
        }

        val builder = AlertDialog.Builder(requireActivity(), R.style.AlertDialogStyle)
        val alertBinding: DialogAddActorBinding = DataBindingUtil.inflate(this.layoutInflater, R.layout.dialog_add_actor, null, false)
        alertBinding.lifecycleOwner = this
        alertBinding.addActorViewModel = addActorViewModel

        with(builder)
        {
            setTitle(R.string.alert_add_actor_title)
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