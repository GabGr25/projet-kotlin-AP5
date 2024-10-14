package com.example.projet_kotlin_ap5.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "song")
data class SongEntity(
    @PrimaryKey
    val id: Long = 0,
    val relativePathName: String = "eg",
    val name: String = "ef",
    val duration: Int = 0

)
