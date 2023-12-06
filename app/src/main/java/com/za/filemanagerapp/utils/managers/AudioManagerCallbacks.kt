package com.za.filemanagerapp.utils.managers

import com.za.filemanagerapp.features.audio.domain.model.Audio

interface AudioManagerCallbacks {
    fun onProgressChange(progress :Long)
    fun onAudioChange(audio:Audio)
    fun onStateChange(state :AudioState)
}