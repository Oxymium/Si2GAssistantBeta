package com.oxymium.si2gassistant.utils

import android.view.View
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.oxymium.si2gassistant.R

// -------------
// IssuesBinders
// -------------

class IssuesBinders {

    companion object {

        /*
        Toggle visibility of search field
         */
        @JvmStatic
        @BindingAdapter("app:toggleIssuesSearchFieldVisibility")
        fun toggleSearchFieldVisibility(view: View, state: Boolean) {
            when (state){
                // Hide
                false -> view.visibility = View.GONE
                // Display
                true -> view.visibility = View.VISIBLE
            }
        }


        /*
        Display ascending/descending icon according to state
         */
        @JvmStatic
        @BindingAdapter("app:dateSortingOrder")
        fun setDateSortingImageView(imageView: ImageView, state: Int?) {
            when (state){
                // Default descending order
                0 -> imageView.setImageDrawable(ResourcesCompat.getDrawable(imageView.resources, R.drawable.circle_calendar_descending, null))
                // Ascending order
                1 -> imageView.setImageDrawable(ResourcesCompat.getDrawable(imageView.resources, R.drawable.circle_calendar_ascending, null))
            }
        }
    }
}