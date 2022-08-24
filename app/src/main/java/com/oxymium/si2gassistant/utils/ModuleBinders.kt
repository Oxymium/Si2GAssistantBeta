package com.oxymium.si2gassistant.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter

// -------------
// ModuleBinders
// -------------

class ModuleBinders {

    companion object {

        /*
      Toggle Cross visibility
       */
        @JvmStatic
        @BindingAdapter("app:moduleCrossVisibility")
        fun toggleModuleCrossVisibility(imageView: ImageView, validated: Boolean) {
            when (validated){
                false -> imageView.visibility = View.VISIBLE
                true -> imageView.visibility = View.INVISIBLE
            }
        }

        /*
       Toggle Check visibility
        */
        @JvmStatic
        @BindingAdapter("app:moduleCheckVisibility")
        fun toggleModuleCheckVisibility(imageView: ImageView, validated: Boolean) {
            when (validated){
                false -> imageView.visibility = View.INVISIBLE
                true -> imageView.visibility = View.VISIBLE
            }
        }

        /*
        Remove possibility to interact with Views when SU
         */
        @JvmStatic
        @BindingAdapter("app:disableModulesView")
        fun disableModuleViews(imageView: ImageView, boolean: Boolean) {
            when (boolean) {
                false -> imageView.isEnabled = true
                true -> imageView.isEnabled = false
            }
        }
    }
}