package com.oxymium.si2gassistant.features.userlogin

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

        return binding.root

    }

}
