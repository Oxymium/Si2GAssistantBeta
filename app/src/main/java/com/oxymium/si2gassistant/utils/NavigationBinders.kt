package com.oxymium.si2gassistant.utils

import androidx.databinding.BindingAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.oxymium.si2gassistant.R

class NavigationBinders {

    companion object{

        // Switch bottom navigationView menu
        @JvmStatic
        @BindingAdapter("app:bottomNavigationView")
        fun switchBottomNavigationView(bottomNavigationView: BottomNavigationView, state: Int?) {

            bottomNavigationView.menu.clear()

            when (state){
                0 -> bottomNavigationView.inflateMenu(R.menu.navigation_menu)
                1 -> {
                    bottomNavigationView.inflateMenu(R.menu.navigation_menu_normal_user)
                    //bottomNavigationView.selectedItemId = R.id.userFragment
                }
                2 -> {
                    bottomNavigationView.inflateMenu(R.menu.navigation_menu_super_user)
                    //bottomNavigationView.selectedItemId = R.id.userFragment
                }
            }

        }
    }
}