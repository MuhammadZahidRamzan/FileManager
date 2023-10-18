package com.za.filemanagerapp.di

import android.content.Context
import com.za.filemanagerapp.features.audio.data.repository.AudioRepositoryImpl
import com.za.filemanagerapp.features.audio.domain.repository.AudioRepository
import com.za.filemanagerapp.features.document.data.repository.DocumentRepositoryImpl
import com.za.filemanagerapp.features.document.domain.repository.DocumentRepository
import com.za.filemanagerapp.features.video.data.repository.VideoRepositoryImpl
import com.za.filemanagerapp.features.video.domain.repository.VideoRepository
import com.za.filemanagerapp.utils.managers.FileManager
import com.za.filemanagerapp.utils.managers.FileManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideAudioRepository( fileManager: FileManager): AudioRepository {
        return AudioRepositoryImpl(fileManager)
    }

    @Provides
    @Singleton
    fun provideFileManager(@ApplicationContext context: Context): FileManager {
        return FileManagerImpl(context)
    }

    @Provides
    @Singleton
    fun provideVideoRepository( fileManager: FileManager): VideoRepository {
        return VideoRepositoryImpl(fileManager)
    }

    @Provides
    @Singleton
    fun provideDocumentRepository( fileManager: FileManager): DocumentRepository {
        return DocumentRepositoryImpl(fileManager)
    }

}