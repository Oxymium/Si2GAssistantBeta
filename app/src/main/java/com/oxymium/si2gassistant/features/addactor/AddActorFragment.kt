package com.oxymium.si2gassistant.features.addactor

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
import com.oxymium.si2gassistant.databinding.DialogAddActorBinding
import com.oxymium.si2gassistant.databinding.FragmentAddActorBinding
import com.oxymium.si2gassistant.navigation.NavigationViewModel
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

        fragmentAddActorBinding.addActorViewModel = addActorViewModel

        // Bind header, body & upload parts
        fragmentAddActorBinding.fragmentAddActorHeaderInclude.addActorViewModel = addActorViewModel
        fragmentAddActorBinding.fragmentAddActorHeaderInclude.navigationViewModel = navigationViewModel
        fragmentAddActorBinding.layoutAddActorBodyInclude.addActorViewModel = addActorViewModel
        fragmentAddActorBinding.fragmentAddActorUploadInclude.addActorViewModel = addActorViewModel

        // Copy current academy
        addActorViewModel.academy.value = navigationViewModel.currentAcademy.value

        // Sphere animation
        val animatedSphere = fragmentAddActorBinding.fragmentAddActorUploadInclude.layoutAddActorCircle.drawable as AnimatedVectorDrawable
        animatedSphere.start()
        animatedSphere.registerAnimationCallback(
            object : Animatable2.AnimationCallback(){
                override fun onAnimationEnd(drawable: Drawable?) {
                    fragmentAddActorBinding.fragmentAddActorUploadInclude.layoutAddActorCircle.post{ (fragmentAddActorBinding.fragmentAddActorUploadInclude.layoutAddActorCircle.drawable as AnimatedVectorDrawable).start() }
                }
            }
        )

        observeAddActorButtonState()
        observeRoleFieldState()
        observeNotificationTrigger()

        return binding.root

    }

    // ---------
    // OBSERVERS
    // ---------

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

    private fun observeNotificationTrigger(){
        addActorViewModel.triggerCreatedActorNotification.observe(viewLifecycleOwner){
            if (it) {
                view?.let { view -> Snackbar.make(view, "New actor successfully pushed", Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    // --------------
    // DIALOG WINDOWS
    // --------------

    private fun displayRoleDialog() {

        // Fetch roles array from res
        val roles = resources.getStringArray(R.array.roles_array)

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
            addActorViewModel.pushActor()
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