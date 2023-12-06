package com.za.filemanagerapp.utils.managers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.za.filemanagerapp.utils.Constants.NOTIFICATION_CHANNEL_ID
import com.za.filemanagerapp.utils.Constants.NOTIFICATION_CHANNEL_NAME

object AppNotificationManager {
    fun Context.createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }

    }
}