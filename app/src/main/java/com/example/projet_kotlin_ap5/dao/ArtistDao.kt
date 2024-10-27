package com.example.projet_kotlin_ap5.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.projet_kotlin_ap5.entities.ArtistEntity

@Dao
interface ArtistDao {
    @Query("SELECT * FROM artist")
    suspend fun getAllArtists(): List<ArtistEntity>

    @Query("SELECT * FROM artist WHERE artist.id = :id")
    suspend fun getArtistById(id: Long): ArtistEntity?

    @Query("SELECT * FROM artist WHERE artist.name LIKE :requiredArtist")
    suspend fun getArtistByName(requiredArtist: String): ArtistEntity?

    @Insert
    suspend fun insertOne(artistEntity: ArtistEntity)

    @Update
    suspend fun updateOne(artistEntity: ArtistEntity): Int

    @Query("DELETE FROM artist")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM artist")
    suspend fun getArtistsCount(): Int

}