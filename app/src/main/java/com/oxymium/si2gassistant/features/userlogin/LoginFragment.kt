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
import com.google.android.material.snackbar.Snackbar
import com.oxymium.si2gassistant.R
import com.oxymium.si2gassistant.databinding.DialogLoginBinding
import com.oxymium.si2gassistant.databinding.FragmentLoginBinding
import com.oxymium.si2gassistant.navigation.NavigationViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

// --------------
// LOGIN FRAGMENT
// --------------

class LoginFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var fragmentLoginBinding: FragmentLoginBinding
    private val binding get() = fragmentLoginBinding

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

        fragmentLoginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        fragmentLoginBinding.lifecycleOwner = activity
        fragmentLoginBinding.navigationViewModel = navigationViewModel

        // Sphere animation
        val animatedSphere = fragmentLoginBinding.fragmentLoginAnimatedSphere.drawable as AnimatedVectorDrawable
        animatedSphere.start()
        animatedSphere.registerAnimationCallback(
            object : Animatable2.AnimationCallback(){
                override fun onAnimationEnd(drawable: Drawable?) {
                    fragmentLoginBinding.fragmentLoginAnimatedSphere.post{ (fragmentLoginBinding.fragmentLoginAnimatedSphere.drawable as AnimatedVectorDrawable).start() }
                }
            }
        )

        // On LoginFragment Creation, always display basic menu
        navigationViewModel.navigationState.value = 0

        observeLoginButtonState()
        observeAuthState()
        observeUserState()
        observeSnackBarErrorMessage()

        return binding.root

    }

    private fun observeLoginButtonState(){
        navigationViewModel.loginButtonClicked.observe(viewLifecycleOwner){ if (it) { displayLoginDialog() } }
    }

    private fun observeAuthState(){
        userLoginViewModel.auth.observe(viewLifecycleOwner){
            if (it != null){
                // Fetch User corresponding to Auth UID in Firestore
                userLoginViewModel.getFirestoreUser(it.uid)
            }
        }
    }

    private fun observeUserState(){
        userLoginViewModel.user.observe(viewLifecycleOwner){
            if (it != null){

                navigationViewModel.currentUser.value = it

                if (it.superUser) {
                    navigationViewModel.navigationState.value = 2
                }else{
                    navigationViewModel.navigationState.value = 1
                }
            }
        }
    }


    private fun observeSnackBarErrorMessage(){
        userLoginViewModel.snackBarErrorMessage.observe(viewLifecycleOwner){
            if (it != null){
                view?.let { view -> Snackbar.make(view, it.toString(), Snackbar.LENGTH_LONG).show() }
            }
        }
    }

    // Display login dialog window
    private fun displayLoginDialog() {

        val negativeButtonClick = { _: DialogInterface, _: Int ->
            // Reset login state back to false
            navigationViewModel.loginButtonClicked.value = false }
        val positiveButtonClick = { _: DialogInterface, _: Int ->
            navigationViewModel.loginButtonClicked.value = false
            // Fetch FirebaseUser auth
            userLoginViewModel.getAuthUid(userLoginViewModel.mail.value, userLoginViewModel.password.value)
            Unit
        }

        val builder = AlertDialog.Builder(requireActivity(), R.style.AlertDialogStyle)
        val alertBinding: DialogLoginBinding = DataBindingUtil.inflate(this.layoutInflater, R.layout.dialog_login, null, false)
        alertBinding.lifecycleOwner = this
        alertBinding.userLoginViewModel = userLoginViewModel

        with(builder)
        {
            setTitle(R.string.alert_login_title)
            setMessage(R.string.alert_login_message)
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