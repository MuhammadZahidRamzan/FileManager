package com.za.filemanagerapp.features.video.domain.repository

import com.za.filemanagerapp.features.video.domain.model.Video

interface VideoRepository {
    fun getVideoFiles():List<Video>
}