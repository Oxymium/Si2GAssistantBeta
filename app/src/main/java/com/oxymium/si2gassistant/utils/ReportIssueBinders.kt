package com.oxymium.si2gassistant.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.Slider
import com.oxymium.si2gassistant.R
import com.oxymium.si2gassistant.model.CreationState

class ReportIssueBinders {

    companion object {

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

            when (gravity) {
                1f -> imageView.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        imageView.resources,
                        R.drawable.circles_weak,
                        null
                    )
                )
                2f -> imageView.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        imageView.resources,
                        R.drawable.circles_moderate,
                        null
                    )
                )
                3f -> imageView.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        imageView.resources,
                        R.drawable.circles_critical,
                        null
                    )
                )
            }
        }

        // Change picture according to gravity level
        @JvmStatic
        @BindingAdapter("app:gravityTextDescription")
        fun changeGravityTextDescription(textView: TextView, gravity: Float?) {
            when (gravity) {
                1f -> textView.text = textView.resources.getString(R.string.gravity_level_1)
                2f -> textView.text = textView.resources.getString(R.string.gravity_level_2)
                3f -> textView.text = textView.resources.getString(R.string.gravity_level_3)
            }
        }

        @JvmStatic
        @BindingAdapter("app:displayReportIssueStatus")
        fun displayReportIssueStatus(textView: TextView, creationState: CreationState?) {
            when (creationState) {
                CreationState.UPLOADING -> textView.text =
                    textView.context.resources.getString(R.string.fragment_add_actor_upload_uploading)
                CreationState.SUCCESS -> textView.text =
                    textView.context.resources.getString(R.string.fragment_add_actor_upload_successful)
                CreationState.FAILURE -> textView.text =
                    textView.context.resources.getString(R.string.fragment_add_actor_upload_failed)
                CreationState.AWAITING -> textView.text =
                    textView.context.resources.getString(R.string.fragment_add_actor_upload_awaiting_hint)
                null -> {}
            }
        }

        /*
   Disable button while uploading to prevent multiple unwanted clicks
   Enable it back in all cases
    */
        @JvmStatic
        @BindingAdapter("app:toggleReportIssueButton")
        fun toggleReportIssueButtons(
            floatingActionButton: FloatingActionButton,
            creationState: CreationState?
        ) {
            when (creationState) {
                CreationState.UPLOADING -> floatingActionButton.isEnabled = false
                CreationState.SUCCESS -> floatingActionButton.isEnabled = true
                CreationState.FAILURE -> floatingActionButton.isEnabled = true
                CreationState.AWAITING -> floatingActionButton.isEnabled = true
                null -> {}
            }
        }
    }
}