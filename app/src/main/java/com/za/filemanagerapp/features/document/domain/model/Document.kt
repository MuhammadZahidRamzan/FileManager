package com.za.filemanagerapp.features.document.domain.model

import android.net.Uri

data class Document(
    private val id: String,
    val title: String,
    private val dateAdded: Long,
    val size: String,
    val album: String?,
    val path:String?,
    val artUri: Uri
)