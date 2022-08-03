package com.oxymium.si2gassistant.features.actors

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.oxymium.si2gassistant.R
import com.oxymium.si2gassistant.databinding.FragmentActorsBinding
import com.oxymium.si2gassistant.navigation.NavigationViewModel
import com.oxymium.si2gassistant.utils.CustomLayoutManager
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

// ---------------
// ACTORS FRAGMENT
// ---------------

class ActorsFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // RecyclerView Adapter
    private lateinit var actorsAdapter: ActorsAdapter

    // DataBinding
    private lateinit var fragmentActorsBinding: FragmentActorsBinding
    private val binding get() = fragmentActorsBinding

    // NavigationViewModel
    private val navigationViewModel by sharedViewModel<NavigationViewModel>()

    private val actorsViewModel by viewModel<ActorsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(fragmentTAG, "onCreate: ")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fragmentActorsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_actors, container, false)
        fragmentActorsBinding.lifecycleOwner = activity
        fragmentActorsBinding.navigationViewModel = navigationViewModel

        // Bind viewModels to included layout
        fragmentActorsBinding.fragmentActorsResultsInclude.actorsViewModel = actorsViewModel
        fragmentActorsBinding.fragmentActorsResultsInclude.navigationViewModel = navigationViewModel

        fragmentActorsBinding.fragmentActorsRecyclerView.layoutManager = CustomLayoutManager(context)

        // Observe actor list
        actorsViewModel.filteredActors.observe(viewLifecycleOwner) { actors ->
            actorsAdapter.submitList(actors)
        }

        // Pass listener to adapter
        actorsAdapter = ActorsAdapter(
            ActorListener{
                Log.d("Item: Actor", "$it")
                navigationViewModel.selectedActor.value = it
            }
        )

        // Attach adapter to recyclerView
        fragmentActorsBinding.fragmentActorsRecyclerView.adapter = actorsAdapter

        observeSelectedAcademy()

        return binding.root

    }

    private fun observeSelectedAcademy(){
        navigationViewModel.selectedAcademy.observe(viewLifecycleOwner){
            if (it != null){
                lifecycleScope.launch { actorsViewModel.getActorsByAcademyId(it.id) }
            }
        }
    }

}
