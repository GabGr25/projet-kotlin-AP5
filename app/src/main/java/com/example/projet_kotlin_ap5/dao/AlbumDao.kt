package com.example.projet_kotlin_ap5.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.projet_kotlin_ap5.entities.AlbumEntity
import com.example.projet_kotlin_ap5.entities.SongEntity

interface AlbumDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(albums: List<AlbumEntity>)

    @Insert
    suspend fun insertOne(albumEntity: AlbumEntity)

    @Update
    suspend fun updateOne(albumEntity: AlbumEntity): Int

    @Query("SELECT * FROM album")
    fun getAllAlbums(): List<AlbumEntity>

    @Query("SELECT * FROM album WHERE album.artist = :artist")
    suspend fun getAlbumsFromArtist(artist: String): List<AlbumEntity>

    @Query("SELECT * FROM album WHERE album.id = :id")
    suspend fun getAlbumById(id: Long): AlbumEntity?

    @Query("SELECT * FROM album WHERE album.name = :name")
    suspend fun getAlbumByName(name: String): AlbumEntity?

    @Query("DELETE FROM album")
    suspend fun deleteAll()
}