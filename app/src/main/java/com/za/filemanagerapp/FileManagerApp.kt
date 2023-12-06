package com.za.filemanagerapp

import android.app.Application
import com.za.filemanagerapp.utils.managers.AppNotificationManager.createNotificationChannel
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FileManagerApp :Application(){
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }
}