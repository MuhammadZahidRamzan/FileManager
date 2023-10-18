package com.za.filemanagerapp.features.video.domain.model

import android.net.Uri

data class Video(
    private val id: String?,
    val title: String?,
    val duration: Long? = 0,
    val folderName: String?,
    private val size: String?,
    private var path: String?,
    var artUri: Uri?
)
