package com.oxymium.si2gassistant.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.oxymium.si2gassistant.R
import com.oxymium.si2gassistant.model.User
import java.util.*

class UserBinders {

    companion object {

        @JvmStatic
        @BindingAdapter("app:displayUserDateFormat")
        fun displayUserDateFormat(textView: TextView, dateInMillis: Long?) {

            val calendar: Calendar = Calendar.getInstance()
            // Set calendar on today's date
            calendar.timeInMillis = dateInMillis ?: 0

            val dayOfTheWeek: String =
                when (calendar.get(Calendar.DAY_OF_WEEK)) {
                    1 -> "Sunday"
                    2 -> "Monday"
                    3 -> "Tuesday"
                    4 -> "Wednesday"
                    5 -> "Thursday"
                    6 -> "Friday"
                    7 -> "Saturday"
                    else -> "Error"
                }

            val month: String =
                when (calendar.get(Calendar.MONTH)) {
                    0 -> "Jan"
                    1 -> "Feb"
                    2 -> "Mar"
                    3 -> "Apr"
                    4 -> "May"
                    5 -> "Jun"
                    6 -> "Jul"
                    7 -> "Aug"
                    8 -> "Sep"
                    9 -> "Oct"
                    10 -> "Nov"
                    11 -> "Dec"
                    else -> "Error"
                }

            textView.text = String.format(textView.context.getString(R.string.fragment_user_date), dayOfTheWeek, calendar.get(Calendar.DAY_OF_MONTH), month, calendar.get(Calendar.YEAR).toString())
        }

        // Greetings text
        @JvmStatic
        @BindingAdapter("app:greetingsText")
        fun displayGreetingsText(textView: TextView, user: User?) {
            val name = user?.name?.replaceFirstChar { it.uppercase() }
            val firstName = user?.firstName?.replaceFirstChar { it.uppercase() }
            textView.text = String.format(textView.context.getString(R.string.fragment_user_greetings), name, firstName)
        }
    }
}