package com.za.filemanagerapp.features.document.domain.repository

import android.content.Context
import com.za.filemanagerapp.features.audio.domain.model.Audio
import com.za.filemanagerapp.features.document.domain.model.Document

interface DocumentRepository {
    fun getDocumentFiles():List<Document>
}
