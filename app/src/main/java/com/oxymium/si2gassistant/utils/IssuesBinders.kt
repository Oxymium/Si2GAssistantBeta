package com.oxymium.si2gassistant.utils

import android.widget.ImageButton
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.oxymium.si2gassistant.R

// -------------
// IssuesBinders
// -------------

class IssuesBinders {

    companion object {

        /*
        Display ascending/descending icon according to state
         */
        @JvmStatic
        @BindingAdapter("app:dateSortingOrder")
        fun setDateSortingImageView(imageButton: ImageButton, state: Int?) {
            when (state){
                // Default descending order
                0 -> imageButton.setImageDrawable(ResourcesCompat.getDrawable(imageButton.resources, R.drawable.sort_calendar_descending, null))
                // Ascending order
                1 -> imageButton.setImageDrawable(ResourcesCompat.getDrawable(imageButton.resources, R.drawable.sort_calendar_ascending, null))
            }
        }
    }
}