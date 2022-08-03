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
import com.oxymium.si2gassistant.utils.CustomLayoutManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

// ------------------
// ACADEMIES FRAGMENT
// ------------------

class AcademiesFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // RecyclerView Adapter
    private lateinit var academiesAdapter: AcademiesAdapter

    // DataBinding
    private lateinit var fragmentAcademiesBinding: FragmentAcademiesBinding
    private val binding get() = fragmentAcademiesBinding

    // NavigationViewModel
    private val navigationViewModel by sharedViewModel<NavigationViewModel>()

    // AcademiesViewModel
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
        fragmentAcademiesBinding.academiesViewModel = academiesViewModel

        fragmentAcademiesBinding.fragmentAcademiesResultsInclude.academiesViewModel = academiesViewModel

        fragmentAcademiesBinding.fragmentAcademiesRecyclerView.layoutManager = CustomLayoutManager(context)

        // Observe actor list
        academiesViewModel.filteredAcademies.observe(viewLifecycleOwner) { academies ->
            academiesAdapter.submitList(academies)
        }

        // Pass listener to adapter
        academiesAdapter = AcademiesAdapter(
            AcademyListener{
                Log.d("Item: Academy", "$it")
                navigationViewModel.selectedAcademy.value = it
            }
        )

        // Attach adapter to recyclerView
        fragmentAcademiesBinding.fragmentAcademiesRecyclerView.adapter = academiesAdapter

        observeAcademiesQuickSearch()

        return binding.root

    }

    private fun observeAcademiesQuickSearch(){
        academiesViewModel.quickSearchAcademies.observe(viewLifecycleOwner){
            if (it != null) {
                academiesViewModel.quickSearchAcademies(it)
            }
        }
    }

}