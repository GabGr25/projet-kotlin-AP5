package com.example.projet_kotlin_ap5.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "song")
data class SongEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String = "Unknown Song Title",
    val albumId: Long = 0,
    val artistId: Long = 0,
    val duration: Int,
    val fileName: String,
    val pathName: String,
    val lyrics: String = "No lyrics",
)
