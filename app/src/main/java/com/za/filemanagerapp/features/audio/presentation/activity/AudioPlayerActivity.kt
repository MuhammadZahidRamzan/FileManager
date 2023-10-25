package com.za.filemanagerapp.features.audio.presentation.activity

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.SeekBar
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.za.filemanagerapp.R
import com.za.filemanagerapp.databinding.ActivityAudioPlayerBinding
import com.za.filemanagerapp.features.audio.domain.model.Audio
import com.za.filemanagerapp.utils.Constants
import com.za.filemanagerapp.utils.Extensions.parcelable
import com.za.filemanagerapp.utils.Utils.formatDuration

class AudioPlayerActivity : AppCompatActivity() {
    private var audio : Audio? = null
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var binding:ActivityAudioPlayerBinding
    private var isPlaying = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAudioPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getIntentData()
        initViews()
        createMediaPlayer()
        initClicks()
    }

    private fun initClicks() {
        binding.btnPlayPause.setOnClickListener {
            if (isPlaying)
                pauseMusic()
            else
                playMusic()
        }
        binding.sbAudio.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if (p2) mediaPlayer?.seekTo(p1)
            }
            override fun onStartTrackingTouch(p0: SeekBar?) = Unit
            override fun onStopTrackingTouch(p0: SeekBar?) = Unit
        })
    }

    private fun createMediaPlayer(){
        try {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer()
            }
            mediaPlayer?.reset()
            mediaPlayer?.setDataSource(audio?.path)
            mediaPlayer?.prepare()
            binding.tvSeekBarStart.text = mediaPlayer?.currentPosition?.toLong()
                ?.let { formatDuration(it) }
            binding.tvSeekBarEnd.text = mediaPlayer?.duration?.toLong()?.let { formatDuration(it) }
            binding.sbAudio.progress = 0
            binding.sbAudio.max = mediaPlayer?.duration ?:100
            mediaPlayer?.setOnPreparedListener{
                playMusic()
            }
            mediaPlayer?.setOnCompletionListener { player ->
                player.seekTo(0)
                binding.tvSeekBarStart.text = mediaPlayer?.currentPosition?.toLong()
                    ?.let { formatDuration(it) }
                binding.sbAudio.progress = 0
                pauseMusic()
            }
        }catch (e:Exception){e.printStackTrace()}
    }

    private fun getIntentData(){
        audio = intent.parcelable(Constants.AUDIO)
    }

    private fun initViews() {
        binding.tvAudioName.text = audio?.title
        Glide.with(this).load(audio?.artUri)
            .apply(RequestOptions().placeholder(R.drawable.icon_music).centerCrop())
            .into(binding.ivAudioImage)
    }

    override fun onResume() {
        super.onResume()
        if (mediaPlayer?.isPlaying == true){
            binding.btnPlayPause.setIconResource(R.drawable.icon_pause)
        }else{
            binding.btnPlayPause.setIconResource(R.drawable.icon_play)
        }

    }

    override fun onPause() {
        super.onPause()
        pauseMusic()
    }



    private fun playMusic(){
        binding.btnPlayPause.setIconResource(R.drawable.icon_pause)
        isPlaying = true
        mediaPlayer?.start()
        handler.post(runnable)

    }
    private fun pauseMusic(){
        binding.btnPlayPause.setIconResource(R.drawable.icon_play)
        isPlaying = false
        mediaPlayer?.pause()
        handler.removeCallbacks(runnable)

    }

    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            binding.tvSeekBarStart.text =
                formatDuration(mediaPlayer?.currentPosition?.toLong() ?: 0)
            binding.sbAudio.progress = mediaPlayer?.currentPosition ?:0
            handler.postDelayed( this, 200)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}