package com.oxymium.si2gassistant.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.slider.Slider
import com.oxymium.si2gassistant.R

class ReportIssueBinders {

    companion object{

        // SLIDER VALUES - 2-WAYS DATA-BINDING
        @JvmStatic
        @InverseBindingAdapter(attribute = "android:value")
        fun getSliderValue(slider: Slider) = slider.value

        @JvmStatic
        @BindingAdapter("android:valueAttrChanged")
        fun setSliderListeners(slider: Slider, attrChange: InverseBindingListener) {
            slider.addOnChangeListener { _, _, _ ->
                attrChange.onChange()
            }
        }

        // Change picture according to gravity level
        @JvmStatic
        @BindingAdapter("app:gravityImageView")
        fun displayGravityPicture(imageView: ImageView, gravity: Float?) {

            when (gravity){
                1f -> imageView.setImageDrawable(ResourcesCompat.getDrawable(imageView.resources, R.drawable.circles_weak_close, null))
                2f -> imageView.setImageDrawable(ResourcesCompat.getDrawable(imageView.resources, R.drawable.circles_moderate_close, null))
                3f -> imageView.setImageDrawable(ResourcesCompat.getDrawable(imageView.resources, R.drawable.circles_critical_close, null))
            }
        }

        // Change picture according to gravity level
        @JvmStatic
        @BindingAdapter("app:gravityTextDescription")
        fun changeGravityTextDescription(textView: TextView, gravity: Float?) {

            when (gravity){
                1f -> textView.text = "Weak"
                2f -> textView.text = "Moderate"
                3f -> textView.text = "Critical"
            }
        }
    }

}