package com.za.filemanagerapp.features.audio.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.za.filemanagerapp.features.audio.domain.model.Audio
import com.za.filemanagerapp.features.audio.domain.repository.AudioRepository
import com.za.filemanagerapp.utils.managers.FileManager
import java.io.File
import javax.inject.Inject

class AudioRepositoryImpl @Inject constructor(private val fileManager: FileManager):AudioRepository {
    override fun getAudioFiles(): List<Audio> {
        return fileManager.getAudiosFiles()
    }
}