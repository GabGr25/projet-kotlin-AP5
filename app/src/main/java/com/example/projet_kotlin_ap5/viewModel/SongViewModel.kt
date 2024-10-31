package com.example.projet_kotlin_ap5.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.projet_kotlin_ap5.MusicDatabase
import com.example.projet_kotlin_ap5.api.ApiClient
import com.example.projet_kotlin_ap5.entities.SongEntity
import com.example.projet_kotlin_ap5.services.AudioPlayerService
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SongViewModel(private val database: MusicDatabase) : ViewModel() {

    private val _hasSongs = MutableLiveData<Boolean>()
    val hasSongs: LiveData<Boolean> get() = _hasSongs

    val allSongs: LiveData<List<SongEntity>> = database.songDao().getAllSongsLiveData()

    fun updateLyricsForSong(song: SongEntity): Deferred<Unit> {
        return viewModelScope.async {
            val artist = song.artistId

            // Getting artist name
            val artistName = database.artistDao().getArtistById(artist)?.name

            val title = song.title
            try {
                if (artistName == null) {
                    Log.e("updateLyrics", "Artist not found for song: ${song.title}")
                    throw Exception("Artist not found")
                }

                val response = ApiClient.apiService.getLyrics(artistName, title)
                Log.d("updateLyrics", "Resultat response: $response")

                if (response.isSuccessful) {
                    val lyrics = response.body()?.lyrics

                    if (lyrics != null) {
                        val updatedSong = song.copy(lyrics = lyrics)
                        updateSong(updatedSong) // Mettre à jour la chanson dans la DB
                        Log.d("updateLyrics", "Lyrics updated for song: ${song.title}")
                    } else {
                        val noLyrics = "No lyrics for this song"
                        val updatedSong = song.copy(lyrics = noLyrics)
                        updateSong(updatedSong)
                        Log.d("updateLyrics", "No lyrics found for song: ${song.title}")
                    }
                } else {
                    Log.e("updateLyrics", "Failed to fetch lyrics: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("updateLyrics", "Error fetching lyrics: ${e.message}")
            }
        }
    }

    suspend fun getSongsByAlbumId(albumId: Long): List<SongEntity> {
        return database.songDao().getSongsFromAlbumId(albumId)
    }
    suspend fun getSongsByAlbumName(album: String): List<SongEntity> {
        val albumEntity = database.albumDao().getAlbumByName(album)
        if (albumEntity != null) {
            return database.songDao().getSongsFromAlbumId(albumEntity.id)
        }
        return emptyList()
    }

    suspend fun getSongById(id: Long): SongEntity {
        return database.songDao().getSongById(id)
    }

    fun updateSong(updatedSongEntity: SongEntity){
        viewModelScope.launch {
            try {
                val rowsUpdated = database.songDao().updateOne(updatedSongEntity)
                if (rowsUpdated > 0) {
                    Log.d("dbMusic", "Mise à jour réussie")
                    Log.d("dbMusic", "Nombre de ligne modifié: $rowsUpdated")
                } else {
                    Log.d("dbMusic", "Aucune ligne n'a été mise à jour")
                }
            }  catch (e: Exception) {
                Log.e("dbMusic", "Erreur lors de la mise à jour: ${e.message}")
            }
        }
    }
}