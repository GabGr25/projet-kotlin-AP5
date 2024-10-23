package com.example.projet_kotlin_ap5.services

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.provider.MediaStore
import android.util.Size
import androidx.annotation.RequiresApi
import com.example.projet_kotlin_ap5.MusicDatabase
import com.example.projet_kotlin_ap5.R
import com.example.projet_kotlin_ap5.entities.AlbumEntity
import com.example.projet_kotlin_ap5.entities.SongEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MusicScanner(private val context: Context) {

    private val defaultThumbnail: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.zinzin)

    @RequiresApi(Build.VERSION_CODES.Q)
    suspend fun loadMusicFiles(database: MusicDatabase): List<SongEntity> = withContext(Dispatchers.IO) {
        val musicList = mutableListOf<SongEntity>()
        val contentResolver: ContentResolver = context.contentResolver

        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.RELATIVE_PATH,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.TITLE
        )

        val selection = "${MediaStore.Audio.Media.RELATIVE_PATH} LIKE ?"
        val selectionArgs = arrayOf("Music/%")

        val cursor = contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            null
        )

        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val fileNameColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
            val durationColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
            val pathNameColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.RELATIVE_PATH)
            val albumColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)
            val titleColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val artistColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)

            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
                val fileName = it.getString(fileNameColumn)
                val duration = it.getInt(durationColumn)
                val pathName = it.getString(pathNameColumn).removePrefix("Music/")
                val albumName = it.getString(albumColumn)
                val title = it.getString(titleColumn)
                val artist = it.getString(artistColumn)

                // Checking if album is already in the list
                var album = database.albumDao().getAlbumByName(albumName)
                if (album == null) {
                    // Loading the thumbnail only if the album is not yet saved
                    val thumbnail = loadThumbnailOrDefault(contentResolver, id)

                    album = AlbumEntity(name = albumName, thumbnail = thumbnail, artist = artist)
                    database.albumDao().insertOne(album)
                }

                musicList.add(SongEntity(id, title, album, artist, duration, fileName, pathName))
            }
        }
        musicList
    }
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun loadThumbnail(contentResolver: ContentResolver, songId: Long): Bitmap {
        val uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, songId)
        return contentResolver.loadThumbnail(uri, Size(640, 480), null)
    }

    // TODO: Optimiser le stockage en BD des images par album
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun loadThumbnailOrDefault(contentResolver: ContentResolver, songId: Long): Bitmap {
        try {
            // Tente de charger la miniature
            val uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, songId)
            return contentResolver.loadThumbnail(uri, Size(640, 480), null)
        } catch (e: Exception) {
            // Si une erreur survient, charge l'image par défaut
            return defaultThumbnail
        }
    }


}