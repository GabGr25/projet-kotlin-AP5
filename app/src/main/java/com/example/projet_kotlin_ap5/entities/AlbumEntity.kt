package com.example.projet_kotlin_ap5.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "album")
data class AlbumEntity (
    @PrimaryKey val id: Long = 0,
    val name: String = "Unknown Album",
    val artist: ArtistEntity,
)