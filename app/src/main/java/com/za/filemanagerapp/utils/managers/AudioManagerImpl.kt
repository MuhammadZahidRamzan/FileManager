package com.za.filemanagerapp.utils.managers

import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import com.za.filemanagerapp.features.audio.domain.model.Audio
import com.za.filemanagerapp.features.audio.utils.Utils.setSongPosition
import java.io.IOException
import javax.inject.Inject

class AudioManagerImpl @Inject constructor(private val context: Context) : AudioManager {
    private var audioPlayer: MediaPlayer? = null
    private var audiosList: List<Audio>? = null
    private var currentAudio: Audio? = null
    private val handler = Handler(Looper.getMainLooper())
    private var currentAudioPosition = 0
    private var callbacks: AudioManagerCallbacks? = null


    override fun play() {
        playAudio()
    }

    override fun pause() {
        pauseAudio()
    }

    override fun setAudio(audio: Audio) {
        currentAudio = audio
        currentAudioPosition = audiosList?.indexOf(currentAudio) ?: 0
        initMediaPlayer()
    }

    override fun setAudios(audios: List<Audio>) {
        audiosList = audios
    }

    override fun playNext() {
        playNextAudio()
    }

    override fun playPrevious() {
        playPrevAudio()
    }



    override fun setCallbacks(callbacks: AudioManagerCallbacks) {
        this.callbacks = callbacks
    }

    override fun isAudioPlaying(): Boolean {
        return audioPlayer?.isPlaying == true
    }

    override fun seekTo(progress: Int) {
        audioPlayer?.seekTo(progress)
    }

    private fun initMediaPlayer() {
        try {
            if (audioPlayer == null) {
                audioPlayer = MediaPlayer()
            }
            audioPlayer?.reset()
            audioPlayer?.setDataSource(currentAudio?.path)
            audioPlayer?.prepareAsync()
            audioPlayer?.setOnPreparedListener {
                callbacks?.onStateChange(AudioState.READY)
                currentAudio?.let { it1 -> callbacks?.onAudioChange(it1) }
            }
            audioPlayer?.setOnCompletionListener {
                callbacks?.onStateChange(AudioState.COMPLETED)
                audioPlayer?.seekTo(0)
                callbacks?.onProgressChange(0)
                pauseAudio()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun playAudio() {
        audioPlayer?.start()
        handler.post(runnable)
        callbacks?.onStateChange(AudioState.PLAYING)
//        AudioService.mediaPlayer?.start()

    }

    private fun pauseAudio() {
        audioPlayer?.pause()
        handler.removeCallbacks(runnable)
        callbacks?.onStateChange(AudioState.PAUSED)
//        AudioService.mediaPlayer?.pause()
    }

    private val runnable = object : Runnable {
        override fun run() {
            audioPlayer?.currentPosition?.let { callbacks?.onProgressChange(it.toLong()) }
            handler.postDelayed(this, 200)
        }
    }

    private fun playNextAudio() {
        if (currentAudioPosition < (audiosList?.lastIndex ?: 0)){
            currentAudioPosition++
            currentAudio = audiosList?.get(currentAudioPosition)
            initMediaPlayer()
        }
    }

    private fun playPrevAudio() {
        if (currentAudioPosition > 0){
            currentAudioPosition--
            currentAudio = audiosList?.get(currentAudioPosition)
            initMediaPlayer()
        }
    }
    override fun release() {
        audioPlayer?.release()
    }

}