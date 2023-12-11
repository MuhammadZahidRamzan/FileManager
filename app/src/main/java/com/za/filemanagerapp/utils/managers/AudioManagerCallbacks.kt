package com.za.filemanagerapp.utils.managers

import com.za.filemanagerapp.features.audio.domain.model.Audio
import com.za.filemanagerapp.utils.enums.AudioState

interface AudioManagerCallbacks {
    fun onProgressChange(progress :Long)
    fun onAudioChange(audio:Audio)
    fun onStateChange(state : AudioState)
}