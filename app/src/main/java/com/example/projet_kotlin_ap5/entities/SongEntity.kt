package com.example.projet_kotlin_ap5.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "song")
data class SongEntity(
    @PrimaryKey val id: Long = 0,
    val title: String,
    val album: String,
    val artist: String,
    val duration: Int,
    val fileName: String,
    val pathName: String
    // TODO: Ajouter les champs supplémentaires
)
