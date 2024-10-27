package com.example.projet_kotlin_ap5.entities

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "album")
data class AlbumEntity (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String = "Unknown Album",
    val thumbnail: Bitmap? = null,
    val artistId: Long? = null,
)