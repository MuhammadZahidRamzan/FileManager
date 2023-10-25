package com.za.filemanagerapp.features.video.domain.model

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class Video(
    val id: String?,
    val title: String?,
    val duration: Long?,
    val folderName: String?,
    val size: String?,
    val path: String?,
    val artUri: Uri?
):Parcelable
