package com.za.filemanagerapp.features.video.data.repository

import com.za.filemanagerapp.features.video.domain.model.Video
import com.za.filemanagerapp.features.video.domain.repository.VideoRepository
import com.za.filemanagerapp.utils.managers.FileManager
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(private val fileManager: FileManager):VideoRepository {
    override fun getVideoFiles(): List<Video> {
        return fileManager.getVideoFiles()
    }
}