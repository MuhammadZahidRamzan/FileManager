package com.za.filemanagerapp.features.audio.service

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.IBinder
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.za.filemanagerapp.R
import com.za.filemanagerapp.features.audio.domain.model.Audio
import com.za.filemanagerapp.features.audio.presentation.activity.AudioPlayerActivity
import com.za.filemanagerapp.features.audio.presentation.fragment.AudioFragment
import com.za.filemanagerapp.utils.Constants.ACTION_START
import com.za.filemanagerapp.utils.Constants.ACTION_STOP
import com.za.filemanagerapp.utils.Constants.EXIT
import com.za.filemanagerapp.utils.Constants.NOTIFICATION_CHANNEL_ID
import com.za.filemanagerapp.utils.Constants.NOTIFICATION_ID
import com.za.filemanagerapp.utils.Utils
import com.za.filemanagerapp.utils.enums.AudioState
import com.za.filemanagerapp.utils.managers.AudioManagerCallbacks
import javax.inject.Inject

class AudioService : Service() {
    @Inject
    lateinit var audioManager: AudioManager

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action) {
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun showNotification(){
        // Get the layouts to use in the custom notification.
        val notificationLayout = RemoteViews(packageName, R.layout.notification_view)
     ////   notificationLayout.setTextViewText(R.id.tv_notification_song_name,AudioFragment.audioList[AudioPlayerActivity.audioPosition].title)
      //  notificationLayout.setTextViewText(R.id.tv_notification_singer_name,AudioFragment.audioList[AudioPlayerActivity.audioPosition].artist)
        val exit = Intent(baseContext,NotificationReceiver::class.java).setAction(EXIT)
        val exitPendingIntent = PendingIntent.getBroadcast(baseContext,0,exit, PendingIntent.FLAG_UPDATE_CURRENT)
        val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(notificationLayout)
            .build()

        startForeground(NOTIFICATION_ID, notification)
        // notificationManagerCompat.notify(notificationId, customNotification)

    }

    private fun start() {
        showNotification()
    }

    private fun stop() {
        stopSelf()
    }



}