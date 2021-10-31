package com.example.ariel.utils

import android.view.View
import android.view.ViewGroup

fun Any.className(): String = this::class.java.name

fun View.removeFromParent() {
    this.parent?.let {
        (it as ViewGroup).removeView(this)
    }
}

fun CharSequence?.isNotNullOrBlank(): Boolean {
    return this.isNullOrBlank().not()
}