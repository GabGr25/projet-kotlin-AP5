package com.example.projet_kotlin_ap5

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.projet_kotlin_ap5.entities.SongEntity

@Dao
interface SongDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(musics: List<SongEntity>)

    @Insert
    suspend fun insertOne(songEntity: SongEntity)

    @Query("SELECT * FROM song")
    fun getAllSongsLiveData(): LiveData<List<SongEntity>>

    @Query("SELECT * FROM song")
    suspend fun getAllSongs(): List<SongEntity>

    @Query("DELETE FROM song")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM song")
    suspend fun getSongsCount(): Int


    // Getting by Filters

    // By Album
    @Query("SELECT * FROM song WHERE song.album LIKE :requiredAlbum")
    suspend fun getSongsFromAlbum(requiredAlbum: String): List<SongEntity>

    // By Artist
    @Query("SELECT * FROM song WHERE song.artist LIKE :requiredArtist")
    suspend fun getSongsFromArtist(requiredArtist: String): List<SongEntity>

    // Getting All Albums by artist
    @Query("SELECT album FROM song WHERE song.artist LIKE :requiredArtist GROUP BY album")
    suspend fun getAlbumsFromArtist(requiredArtist: String): List<String>


    // Getting All Artists
    @Query("SELECT artist FROM song GROUP BY artist")
    suspend fun getArtists(): List<String>
}
