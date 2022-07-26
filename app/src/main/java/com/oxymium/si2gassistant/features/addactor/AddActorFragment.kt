package com.oxymium.si2gassistant.features.addactor

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.oxymium.si2gassistant.R
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

        fragmentAddActorBinding.navigationViewModel = navigationViewModel

        return binding.root

    }

}