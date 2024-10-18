package com.example.projet_kotlin_ap5.services

import android.content.ContentResolver
import android.content.Context
import android.provider.MediaStore
import com.example.projet_kotlin_ap5.MusicDatabase
import com.example.projet_kotlin_ap5.entities.SongEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MusicScanner(private val context: Context) {

    suspend fun loadMusicFiles(): List<SongEntity> = withContext(Dispatchers.IO) {
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
                val album = it.getString(albumColumn)
                val title = it.getString(titleColumn)
                val artist = it.getString(artistColumn)

                musicList.add(SongEntity(id, title, album, artist, duration, fileName, pathName))
            }
        }
        musicList
    }

}