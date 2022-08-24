package com.oxymium.si2gassistant.utils

import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.oxymium.si2gassistant.R
import com.oxymium.si2gassistant.model.CreationState

class AddActorBinders {

    companion object {

        @JvmStatic
        @BindingAdapter("app:animateImageView")
        fun animateImageView(imageView: ImageView, state: Boolean) {
            when (state){
                // Hide
                false -> {
                    imageView.isEnabled = false
                    imageView.clearAnimation()
                }
                // Display
                true -> {
                    imageView.isEnabled = true
                    val animation = AnimationUtils.loadAnimation(imageView.context, R.anim.zoom_in)
                    imageView.startAnimation(animation)
                }
            }
        }

        @JvmStatic
        @BindingAdapter("app:displayAddActorText")
        fun displayAddActorText(textView: TextView, state: Boolean) {
            when (state){
                // Missing elements text
                false -> {
                    textView.text = textView.context.resources.getString(R.string.fragment_add_actor_upload_ready_negative)
                }
                // Everything ready for upload
                true -> {
                    textView.text = textView.context.resources.getString(R.string.fragment_add_actor_upload_ready_positive)
                }
            }
        }

        @JvmStatic
        @BindingAdapter("app:displayAddActorStatus")
        fun displayAddActorStatus(textView: TextView, creationState: CreationState?) {
            when (creationState){
                CreationState.UPLOADING -> textView.text = textView.context.resources.getString(R.string.fragment_add_actor_upload_uploading)
                CreationState.SUCCESS -> textView.text = textView.context.resources.getString(R.string.fragment_add_actor_upload_successful)
                CreationState.FAILURE -> textView.text = textView.context.resources.getString(R.string.fragment_add_actor_upload_failed)
                CreationState.AWAITING -> textView.text = textView.context.resources.getString(R.string.fragment_add_actor_upload_awaiting_hint)
                null -> {}
                }
            }

        /*
       Disable button while uploading to prevent multiple unwanted clicks
       Enable it back in all cases
        */
        @JvmStatic
        @BindingAdapter("app:toggleAddActorButton")
        fun toggleAddActorButtons(floatingActionButton: FloatingActionButton, creationState: CreationState?) {
            when (creationState){
                CreationState.UPLOADING -> floatingActionButton.isEnabled = false
                CreationState.SUCCESS -> floatingActionButton.isEnabled = true
                CreationState.FAILURE -> floatingActionButton.isEnabled = true
                CreationState.AWAITING -> floatingActionButton.isEnabled = true
                null -> {}
            }
        }
    }

}