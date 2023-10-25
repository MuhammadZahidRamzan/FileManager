package com.za.filemanagerapp.features.audio.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Audio(
    private val id: String?,
    val title: String?,
    val album: String?,
    private val artist: String?,
    val duration: Long? = 0,
    val path: String?,
    val artUri: String?
):Parcelable

