package com.example.projet_kotlin_ap5

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log

fun listMusicFilesScopedStorage(contentResolver: ContentResolver) {
    val musicUri: Uri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI
    val projection = arrayOf(MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.DATA)

    val cursor: Cursor? = contentResolver.query(musicUri, projection, null, null, null)

    cursor?.use {
        val nameColumnIndex = it.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)
        val dataColumnIndex = it.getColumnIndex(MediaStore.Audio.Media.DATA)

        while (it.moveToNext()) {
            val fileName = it.getString(nameColumnIndex)
            val filePath = it.getString(dataColumnIndex)
            Log.d("MusicFile", "Fichier : $fileName, Path : $filePath")
        }
    }
}
