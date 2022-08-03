package com.oxymium.si2gassistant.utils

import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.oxymium.si2gassistant.R

// -------------
// ModuleBinders
// -------------

    class ModuleBinders {

        companion object {

            /*
            Display icon according to Module.validated status
             */
            @JvmStatic
            @BindingAdapter("app:moduleValidatedColor")
            fun setValidatedModuleImageView(imageView: ImageView, validated: Boolean?) {
                when (validated){
                    false -> imageView.setImageDrawable(ResourcesCompat.getDrawable(imageView.resources, R.drawable.module_cross_hexagonal, null))
                    true -> imageView.setImageDrawable(ResourcesCompat.getDrawable(imageView.resources, R.drawable.module_check_hexagonal, null))
                    else -> {}
                }
            }

    }
}