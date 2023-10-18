package com.za.filemanagerapp.features.document.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.za.filemanagerapp.features.audio.domain.model.Audio
import com.za.filemanagerapp.features.audio.domain.repository.AudioRepository
import com.za.filemanagerapp.features.document.domain.model.Document
import com.za.filemanagerapp.features.document.domain.repository.DocumentRepository
import com.za.filemanagerapp.utils.managers.FileManager
import java.io.File
import javax.inject.Inject

class DocumentRepositoryImpl @Inject constructor(private val fileManager: FileManager):DocumentRepository {
    override fun getDocumentFiles(): List<Document> {
        return fileManager.getDocumentFiles()
    }
}