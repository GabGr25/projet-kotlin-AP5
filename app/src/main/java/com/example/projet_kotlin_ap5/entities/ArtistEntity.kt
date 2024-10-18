package com.example.projet_kotlin_ap5.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artist")
data class ArtistEntity (
    @PrimaryKey val id: Long = 0,
    val name: String = "Unknown Artist"
)