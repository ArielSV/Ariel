package com.example.ariel.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.ariel.R
import com.example.ariel.flows.locationsflow.activities.MapActivity

class NotificationHelper(var context: Context, var msg: String) {

    fun notification() {
        createNotificationChannel()
        val senInt = Intent(context, MapActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingInt = PendingIntent.getActivities(context, 0, arrayOf(senInt), 0)
        val icon = BitmapFactory.decodeResource(
            context.resources,
            R.drawable.common_google_signin_btn_icon_dark
        )
        val isNotification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.common_full_open_on_phone)
            .setLargeIcon(icon)
            .setContentTitle("Notification")
            .setContentText(msg)
            .setContentIntent(pendingInt)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        NotificationManagerCompat.from(context)
            .notify(NOTIFICATION_ID, isNotification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = CHANNEL_ID
            val channelDescription = "Channel descrip"
            val imports = NotificationManager.IMPORTANCE_DEFAULT
            val channels = NotificationChannel(CHANNEL_ID, name, imports).apply {
                description = channelDescription
            }
            val notificationManger =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManger.createNotificationChannel(channels)
        }
    }

    private companion object {
        const val CHANNEL_ID = "massage id"
        const val NOTIFICATION_ID = 123
    }
}