package com.example.ariel.utils

import android.app.Activity
import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.ariel.R
import android.net.NetworkInfo

import androidx.core.content.ContextCompat.getSystemService

import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

fun Int?.orZero(): Int = this ?: 0

fun Double?.orZero(): Double = this ?: 0.0

fun loadImages(context: Context, urlImage: String, container: ImageView) {
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()
    Glide
        .with(context)
        .load(urlImage)
        .centerCrop()
        .error(R.drawable.error)
        .placeholder(circularProgressDrawable)
        .into(container)
}

fun verifyAvailableNetwork(activity: Activity): Boolean {
    val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
}