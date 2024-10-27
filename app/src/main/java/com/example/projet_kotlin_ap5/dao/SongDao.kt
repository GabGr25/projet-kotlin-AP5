package com.example.projet_kotlin_ap5.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.projet_kotlin_ap5.entities.SongEntity

@Dao
interface SongDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(musics: List<SongEntity>)

    @Insert
    suspend fun insertOne(songEntity: SongEntity)

    @Update
    suspend fun updateOne(songEntity: SongEntity): Int

    @Query("SELECT * FROM song")
    fun getAllSongsLiveData(): LiveData<List<SongEntity>>

    @Query("SELECT * FROM song")
    suspend fun getAllSongs(): List<SongEntity>

    @Query("SELECT * FROM song WHERE song.id = :id")
    suspend fun getSongById(id: Long): SongEntity

    @Query("DELETE FROM song")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM song")
    suspend fun getSongsCount(): Int

    // By Album
    @Query("SELECT * FROM song WHERE song.albumId LIKE :requiredAlbumId")
    suspend fun getSongsFromAlbumId(requiredAlbumId: Long): List<SongEntity>

    // By Artist
    @Query("SELECT * FROM song WHERE song.artistId LIKE :requiredArtistId")
    suspend fun getSongsFromArtistId(requiredArtistId: Long): List<SongEntity>

    // Getting All Albums by artist
    @Query("SELECT albumId FROM song WHERE song.artistId LIKE :requiredArtistId GROUP BY albumId")
    suspend fun getAlbumsFromArtist(requiredArtistId: Long): List<Long>

}
