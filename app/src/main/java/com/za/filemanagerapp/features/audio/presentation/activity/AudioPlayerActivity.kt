package com.za.filemanagerapp.features.audio.presentation.activity

import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.za.filemanagerapp.R
import com.za.filemanagerapp.databinding.ActivityAudioPlayerBinding
import com.za.filemanagerapp.features.audio.domain.model.Audio
import com.za.filemanagerapp.utils.Constants
import com.za.filemanagerapp.utils.Extensions.parcelable
import com.za.filemanagerapp.utils.Utils.formatDuration
import com.za.filemanagerapp.utils.managers.AudioManager
import com.za.filemanagerapp.utils.managers.AudioManagerCallbacks
import com.za.filemanagerapp.utils.managers.AudioState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AudioPlayerActivity :
    AppCompatActivity() {
    @Inject
    lateinit var audioManager: AudioManager
    private var audio: Audio? = null
    private lateinit var binding: ActivityAudioPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAudioPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getIntentData()
        initViews()
        initClicks()
        initAudioManager()
    }

    private fun getIntentData() {
        audio = intent.parcelable(Constants.AUDIO)
    }

    private fun initViews() {
        binding.sbAudio.isEnabled = false
    }

    private fun initAudioManager() {
        audio?.let { audioManager.setAudio(it) }
        audioManager.setCallbacks(object : AudioManagerCallbacks {
            override fun onStateChange(state: AudioState) {
                when (state) {
                    AudioState.READY -> {
                        enableControls()
                        audioManager.play()
                    }

                    AudioState.PLAYING -> {
                        binding.btnPlayPause.setIconResource(R.drawable.icon_pause)
                    }

                    AudioState.PAUSED -> {
                        binding.btnPlayPause.setIconResource(R.drawable.icon_play)
                    }

                    AudioState.COMPLETED -> {

                    }
                }
            }
            override fun onProgressChange(progress: Long) {
                binding.sbAudio.progress = progress.toInt()
                binding.tvSeekBarStart.text = formatDuration(progress)
            }

            override fun onAudioChange(audio: Audio) {
                binding.sbAudio.max = audio.duration?.toInt() ?:0
                binding.tvAudioName.text = audio.title
                Glide.with(this@AudioPlayerActivity).load(audio.artUri)
                    .apply(RequestOptions().placeholder(R.drawable.icon_music).centerCrop())
                    .into(binding.ivAudioImage)
                binding.tvSeekBarEnd.text = audio.duration?.let { formatDuration(it) }
            }


        })

    }

    private fun enableControls() {
        binding.btnPlayPause.isEnabled = true
        binding.btnPrev.isEnabled = true
        binding.btnNext.isEnabled = true
        binding.sbAudio.isEnabled = true
    }

    private fun initClicks() {
        binding.btnPlayPause.setOnClickListener {
            if (audioManager.isAudioPlaying())
                audioManager.pause()
            else
                audioManager.play()
        }
        binding.sbAudio.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) audioManager.seekTo(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) = Unit
            override fun onStopTrackingTouch(p0: SeekBar?) = Unit
        })
        binding.btnNext.setOnClickListener {
            audioManager.playNext()
        }
        binding.btnPrev.setOnClickListener {
            audioManager.playPrevious()
        }
    }

    override fun onPause() {
        super.onPause()
        audioManager.pause()
    }


    override fun onDestroy() {
      //  audioManager.release()
        super.onDestroy()

    }
}