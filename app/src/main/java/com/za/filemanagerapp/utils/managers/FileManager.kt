package com.za.filemanagerapp.utils.managers

import android.content.Context
import com.za.filemanagerapp.features.audio.domain.model.Audio
import com.za.filemanagerapp.features.document.domain.model.Document
import com.za.filemanagerapp.features.video.domain.model.Video

interface FileManager {
    fun getAudiosFiles():List<Audio>
    fun getVideoFiles():List<Video>
    fun getDocumentFiles():List<Document>
}