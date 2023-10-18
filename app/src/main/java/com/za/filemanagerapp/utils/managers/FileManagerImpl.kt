package com.za.filemanagerapp.utils.managers

import android.R.attr.mimeType
import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.za.filemanagerapp.features.audio.domain.model.Audio
import com.za.filemanagerapp.features.document.domain.model.Document
import com.za.filemanagerapp.features.video.domain.model.Video
import java.io.File
import javax.inject.Inject


class FileManagerImpl @Inject constructor(private val context: Context):FileManager {
    @SuppressLint("Range")
    override fun getAudiosFiles(): List<Audio> {
        val tempList = ArrayList<Audio>()
        val selection = MediaStore.Audio.Media.IS_MUSIC +  " != 0"
        val projection = arrayOf(
            MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.DATE_ADDED,
            MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.ALBUM_ID)
        val cursor = context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection,selection
            ,null, MediaStore.Audio.Media.DATE_ADDED + " DESC", null)
        if (cursor != null){
            if (cursor.moveToNext())
                do {
                    val titleC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                    val idC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
                    val albumC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                    val artistC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    val pathC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val durationC = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                    val albumIdC = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)).toString()
                    val uri = Uri.parse("content://media/external/audio/albumart")
                    val artUriC = Uri.withAppendedPath(uri,albumIdC).toString()
                    val music = Audio(id = idC, title = titleC, album = albumC, artist = artistC, path = pathC, duration = durationC, artUri = artUriC)
                    val file = music.path?.let { File(it) }
                    if (file?.exists() == true){
                        tempList.add(music)
                    }
                }while (cursor.moveToNext())
            cursor.close()
        }
        return tempList
    }

    @SuppressLint("Range")
    override fun getVideoFiles(): List<Video> {
        val tempList = ArrayList<Video>()
        val projection = arrayOf(
            MediaStore.Video.Media.TITLE,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.DATE_ADDED,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.BUCKET_ID
        )
        val cursor = context.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            MediaStore.Video.Media.DATE_ADDED + " DESC "
        )
        if (cursor != null)
            if (cursor.moveToNext())
                do {
                    val titleC = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE))
                    val idC = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media._ID))
                    val folderC = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.BUCKET_DISPLAY_NAME))
                    val sizeC = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.SIZE))
                    val pathC = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA))
                    val durationC = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DURATION)).toLong()
                    try {
                        val file = File(pathC)
                        val artUriC = Uri.fromFile(file)
                        val video = Video(
                            title = titleC,
                            id = idC,
                            folderName = folderC,
                            duration = durationC,
                            size = sizeC,
                            path = pathC,
                            artUri = artUriC
                        )
                        if (file.exists()) tempList.add(video)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } while (cursor.moveToNext())
        cursor?.close()
        return tempList
    }

    @SuppressLint("Range")
    override fun getDocumentFiles(): List<Document> {
        val tempList = ArrayList<Document>()
        val selection = "${MediaStore.Files.FileColumns.MIME_TYPE}='application/pdf'"
        val selectionArgs = arrayOf("application/pdf", "application/msword", "text/plain")
        val projection1 = arrayOf(
            MediaStore.Files.FileColumns.TITLE,
         //   MediaStore.Files.FileColumns.SIZE,
        //    MediaStore.Files.FileColumns._ID,
        //    MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME,
            MediaStore.Files.FileColumns.DATA,
          //  MediaStore.Files.FileColumns.DATE_ADDED,
         //   MediaStore.Files.FileColumns.BUCKET_ID
        )
        val cursor1 = context.contentResolver.query(
            MediaStore.Files.getContentUri("external"),
            projection1,
            selection,
            null,
            null
            /*MediaStore.Files.FileColumns.DATE_ADDED + " DESC "*/
        )
        if (cursor1 != null)
            if (cursor1.moveToNext())
                do {
                    val titleC = cursor1.getString(cursor1.getColumnIndex(MediaStore.Files.FileColumns.TITLE))
                    val idC ="" /*cursor1.getString(cursor1.getColumnIndex(MediaStore.Files.FileColumns._ID))*/
                    val folderC ="" /*cursor1.getString(cursor1.getColumnIndex(MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME))*/
                    val sizeC ="" /*cursor1.getString(cursor1.getColumnIndex(MediaStore.Files.FileColumns.SIZE))*/
                    val pathC = cursor1.getString(cursor1.getColumnIndex(MediaStore.Files.FileColumns.DATA))
                    val dateAddedC =10000L /*cursor1.getLong(cursor1.getColumnIndex(MediaStore.Files.FileColumns.DATE_ADDED))*/
                    try {
                        val file = File(pathC)
                        val artUriC = Uri.fromFile(file)
                        val document1 = Document(
                            title = titleC,
                            id = idC,
                            album = folderC,
                            size = sizeC,
                            path = pathC,
                            artUri = artUriC,
                            dateAdded = dateAddedC
                        )
                        if (file.exists()) tempList.add(document1)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } while (cursor1.moveToNext())
        cursor1?.close()
        return tempList
    }
}