package com.za.filemanagerapp.features.audio.domain.repository

import android.content.Context
import com.za.filemanagerapp.features.audio.domain.model.Audio

interface AudioRepository {
    fun getAudioFiles():List<Audio>
}
