package com.oxymium.si2gassistant.features.userlogin

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
import com.oxymium.si2gassistant.R
import com.oxymium.si2gassistant.databinding.FragmentUserBinding
import com.oxymium.si2gassistant.navigation.NavigationViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

// -------------
// USER FRAGMENT
// -------------

class UserFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var fragmentUserBinding: FragmentUserBinding
    private val binding get() = fragmentUserBinding

    // NavigationViewModel
    private val navigationViewModel by sharedViewModel<NavigationViewModel>()

    // UserLoginViewModel
    private val userLoginViewModel by sharedViewModel<UserLoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(fragmentTAG, "onCreate: ")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fragmentUserBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false)
        fragmentUserBinding.lifecycleOwner = activity
        fragmentUserBinding.navigationViewModel = navigationViewModel

        // Sphere animation
        val animatedSphere = fragmentUserBinding.fragmentUserSpheres.drawable as AnimatedVectorDrawable
        animatedSphere.start()
        animatedSphere.registerAnimationCallback(
            object : Animatable2.AnimationCallback(){
                override fun onAnimationEnd(drawable: Drawable?) {
                    fragmentUserBinding.fragmentUserSpheres.post{ (fragmentUserBinding.fragmentUserSpheres.drawable as AnimatedVectorDrawable).start() }
                }
            }
        )

        observeLogoutButtonState()
        observeUser()
        observeAcademy()

        return binding.root

    }

    // ---------
    // OBSERVERS
    // ---------
    private fun observeUser(){
        userLoginViewModel.user.observe(viewLifecycleOwner){
            if (it != null){
                it.academy?.let { userId ->
                    // Copy User value in Shared Data
                    navigationViewModel.currentUser.value = it
                    userLoginViewModel.getFirestoreAcademy(userId)
                }
            }
        }
    }

    private fun observeAcademy(){
        userLoginViewModel.academy.observe(viewLifecycleOwner){
            if (it != null){
                // Copy Academy value in Shared Data
                navigationViewModel.currentAcademy.value = it
            }
        }
    }

    // --------------
    // LOGOUT PROMPT
    // --------------
    private fun observeLogoutButtonState(){
        navigationViewModel.logoutButtonClicked.observe(viewLifecycleOwner){ if (it) { displayLogoutConfirmDialog() } }
    }


    private fun displayLogoutConfirmDialog() {

        val negativeButtonClick = { _: DialogInterface, _: Int -> navigationViewModel.logoutButtonClicked.value = false }
        val positiveButtonClick = { _: DialogInterface, _: Int ->
            navigationViewModel.logoutButtonClicked.value = false
            // Back to Login screen by nullifying all persisting values
            userLoginViewModel.nullifyUserLoginValues()
            navigationViewModel.navigationState.value = 0
        }

        val builder = AlertDialog.Builder(requireActivity(), R.style.AlertDialogStyle)
        with(builder)
        {
            setTitle(R.string.alert_logout_title)
            setMessage(R.string.alert_logout_message)
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


}
