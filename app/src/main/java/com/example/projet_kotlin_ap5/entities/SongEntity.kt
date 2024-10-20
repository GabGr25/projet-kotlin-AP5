package com.example.projet_kotlin_ap5.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "song")
data class SongEntity(
    @PrimaryKey val id: Long = 0,
    val title: String = "Unknown Song Title",
    val album: String, // TODO: Relier avec AlbumEntity
    val artist: String, // TODO: Relier avec ArtistEntity
    val duration: Int,
    val fileName: String,
    val pathName: String,
    val lyrics: String = "No lyrics",
//    val thumbnail: Bitmap? = null
)
