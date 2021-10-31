package com.example.ariel.views

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.example.ariel.R

class LoadingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val textView by lazy {
        val view = TextView(context)
        view.setPadding(0, resources.getDimensionPixelSize(R.dimen.medium_size), 0, 0)
        view
    }

    init {
        isClickable = true
        orientation = VERTICAL
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        gravity = Gravity.CENTER
        setBackgroundResource(R.color.white_alpha)

        val layoutParamsViews =
            LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParamsViews.gravity = Gravity.CENTER

        val progressBar = ProgressBar(context)
        addView(progressBar, layoutParamsViews)
        addView(textView, layoutParamsViews)
    }

    fun setMessage(message: String) {
        textView.text = message
    }
}
