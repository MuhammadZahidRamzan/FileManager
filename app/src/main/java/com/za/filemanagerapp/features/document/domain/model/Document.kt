package com.za.filemanagerapp.features.document.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Document(
    val id : Long?,
    val path : String?,
    val uri : String?,
    val name :String?,
    val dateTime :Long?,
    val mimeType: String?,
    val size : Long?,
    val folder : String?
):Parcelable