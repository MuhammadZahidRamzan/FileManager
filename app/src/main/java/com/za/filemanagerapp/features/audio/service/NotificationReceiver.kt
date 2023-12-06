package com.za.filemanagerapp.features.audio.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.za.filemanagerapp.utils.Constants.ACTION_STOP
import com.za.filemanagerapp.utils.Constants.EXIT

class NotificationReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, p1: Intent?) {
        when(p1?.action){
            EXIT -> {
//                AudioService.mediaPlayer?.release()
//                AudioService.mediaPlayer = null
//                val serviceIntent =
//                    Intent(context, AudioService::class.java).apply {
//                        action = ACTION_STOP
//                    }
//                context?.stopService(serviceIntent)
            }
        }
    }
}