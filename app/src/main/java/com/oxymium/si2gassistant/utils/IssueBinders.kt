package com.oxymium.si2gassistant.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.oxymium.si2gassistant.R
import java.util.*

// ------------
// IssueBinders
// ------------

class IssueBinders {

    companion object {

        /*
        Color issue depending on gravity level
        1° Weak = yellow
        2° Moderate = orange
        3° Severe = red
         */
        @JvmStatic
        @BindingAdapter("app:issueGravityColor")
        fun setGravityImageView(imageView: ImageView, priority: Int?) {
            when (priority){
                1 -> imageView.setImageDrawable(ResourcesCompat.getDrawable(imageView.resources, R.drawable.issue_yellow, null))
                2 -> imageView.setImageDrawable(ResourcesCompat.getDrawable(imageView.resources, R.drawable.issue_orange, null))
                3 -> imageView.setImageDrawable(ResourcesCompat.getDrawable(imageView.resources, R.drawable.issue_red, null))
            }
        }

        @JvmStatic
        @BindingAdapter("app:setIssueId")
        fun setIssueIdTextView(textView: TextView, id: Int?) { textView.text = String.format(textView.context.getString(R.string.item_issue_id), id) }

        // Convert timeInMillis to date dd/mm/yyyy
        @JvmStatic
        @BindingAdapter("app:timeInMillisToDate")
        fun convertTimeInMillisToDate(textView: TextView, timeInMillis: Long?) {

            val calendar = Calendar.getInstance()
            // Set calendar to this date
            calendar.timeInMillis = timeInMillis ?: 0

            // Add "0" for days < 10, for instance 1 -> 01,  2-> 02 etc.
            val day: String = if (calendar.get(Calendar.DAY_OF_MONTH) < 10){
                "0" + (calendar.get(Calendar.DAY_OF_MONTH).toString())
            }else{
                calendar.get(Calendar.DAY_OF_MONTH).toString()
            }

            val month: String = when (calendar.get(Calendar.MONTH)) {
                0 -> "01"
                1 -> "02"
                2 -> "03"
                3 -> "04"
                4 -> "05"
                5 -> "06"
                6 -> "07"
                7 -> "08"
                8 -> "09"
                9 -> "10"
                10 -> "11"
                11 -> "12"
                else -> "??"
            }

            val year: String = calendar.get(Calendar.YEAR).toString()

            textView.text = String.format(textView.context.getString(R.string.item_issue_date), day, month, year)

        }

    }
}