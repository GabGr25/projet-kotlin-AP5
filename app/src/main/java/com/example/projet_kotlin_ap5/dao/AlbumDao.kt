package com.example.projet_kotlin_ap5.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.projet_kotlin_ap5.entities.AlbumEntity
import com.example.projet_kotlin_ap5.entities.SongEntity

@Dao
interface AlbumDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(albums: List<AlbumEntity>)

    @Insert
    suspend fun insertOne(albumEntity: AlbumEntity)

    @Update
    suspend fun updateOne(albumEntity: AlbumEntity): Int

    @Query("SELECT * FROM album")
    suspend fun getAllAlbums(): List<AlbumEntity>

    @Query("SELECT * FROM album LIMIT 5")
    suspend fun getFirstFiveAlbums(): List<AlbumEntity>

    @Query("SELECT * FROM album WHERE album.artistId = :artistId")
    suspend fun getAlbumsFromArtist(artistId: Long): List<AlbumEntity>

    @Query("SELECT * FROM album WHERE album.id = :id")
    suspend fun getAlbumById(id: Long): AlbumEntity?

    @Query("SELECT * FROM album WHERE album.name = :name")
    suspend fun getAlbumByName(name: String): AlbumEntity?

    @Query("SELECT thumbnail FROM album WHERE album.id = :id")
    suspend fun getAlbumThumbnailById(id: Long): ByteArray?

    @Query("DELETE FROM album")
    suspend fun deleteAll()
}