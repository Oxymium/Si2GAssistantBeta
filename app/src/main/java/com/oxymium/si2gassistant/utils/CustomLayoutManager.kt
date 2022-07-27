package com.oxymium.si2gassistant.utils

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnticipateOvershootInterpolator
import android.view.animation.BounceInterpolator
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import androidx.recyclerview.widget.LinearLayoutManager

// -------------------
// CustomLayoutManager
// -------------------

class CustomLayoutManager : LinearLayoutManager {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, orientation: Int, reverseLayout: Boolean) : super(context, orientation, reverseLayout)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    private val enterInterpolator = AnticipateOvershootInterpolator(10f)
    private val accelerateInterpolator = AccelerateInterpolator()
    private val bounceInterpolator = BounceInterpolator()
    private val slowInInterpolator = LinearOutSlowInInterpolator()

    override fun addView(child: View, index: Int) {
        super.addView(child, index)
        val h = 0f
        // if index == 0 item is added on top if -1 it's on the bottom
        child.translationY = if(index == 0) -h else h
        // begin animation when view is laid out
        child.alpha = 1f
        child.animate()
            .setDuration(250L)
            .translationY(0f).alpha(1f)
            .rotation(-90f)
            .translationX(-2000f)
            .setInterpolator(slowInInterpolator)
            .withEndAction {
                child.animate()
                    .setDuration(250L)
                    .rotation(0f)
                    .translationY(0f).alpha(1f)
                    .translationX(0f)
                    .setInterpolator(slowInInterpolator)
                    .start()
            }
            .start()
    }
}