package com.za.filemanagerapp.features.video.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.za.filemanagerapp.databinding.ActivityVideoPlayerBinding
import com.za.filemanagerapp.features.video.domain.model.Video
import com.za.filemanagerapp.utils.Constants
import com.za.filemanagerapp.utils.Extensions.parcelable

class VideoPlayerActivity : AppCompatActivity() {
    private var video:Video? = null
    private lateinit var videoPlayer: ExoPlayer
    private lateinit var binding: ActivityVideoPlayerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getIntentData()
    }

    private fun getIntentData(){
        video = intent.parcelable(Constants.VIDEO)
        videoPlayer = ExoPlayer.Builder(this).build()
        binding.exoPlayer.player = videoPlayer
        val mediaItem = video?.artUri?.let { MediaItem.fromUri(it) }
        mediaItem?.let { videoPlayer.setMediaItem(it) }
        videoPlayer.prepare()
        videoPlayer.play()
    }

    override fun onPause() {
        super.onPause()
        videoPlayer.pause()
    }
    override fun onDestroy() {
        super.onDestroy()
        videoPlayer.release()
    }

}