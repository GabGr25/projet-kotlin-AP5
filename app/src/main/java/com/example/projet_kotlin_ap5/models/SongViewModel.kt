package com.example.projet_kotlin_ap5.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projet_kotlin_ap5.MusicDatabase
import com.example.projet_kotlin_ap5.entities.SongEntity
import kotlinx.coroutines.launch

class SongViewModel(private val database: MusicDatabase) : ViewModel() {

    private val _hasSongs = MutableLiveData<Boolean>()
    val hasSongs: LiveData<Boolean> get() = _hasSongs

    val allSongs: LiveData<List<SongEntity>> = database.songDao().getAllSongsLiveData()

    fun logAllSongs() {
        viewModelScope.launch {
            try {
                val songs: List<SongEntity> = database.songDao().getAllSongs()
                if (songs.isNotEmpty()) {
                    Log.d("dbMusic", "Liste des chansons dans la base de données :")
                    songs.forEach { song ->
                        Log.d("dbMusic", song.toString())
                    }
                } else {
                    Log.d("dbMusic", "Aucune chanson trouvée dans la base de données.")
                }
            } catch (e: Exception) {
                Log.e("dbMusic", "Erreur lors de la récupération des chansons : ${e.message}")
            }
        }
    }

    fun logSongsByAlbum(album: String) {
        viewModelScope.launch {
            try {
                val songs: List<SongEntity> = database.songDao().getSongsFromAlbum(album)
                if (songs.isNotEmpty()) {
                    Log.d("dbMusic", "Liste des chansons présentes sur cet album :")
                    songs.forEach { song ->
                        Log.d("dbMusic", song.toString())
                    }
                } else {
                    Log.d("dbMusic", "Aucune chanson trouvée pour cet album dans la base de données.")
                }
            }  catch (e: Exception) {
                Log.e("dbMusic", "Erreur lors de la récupération des chansons : ${e.message}")
            }
        }
    }

    fun logSongsByArtist(artist: String) {
        viewModelScope.launch {
            try {
                val songs: List<SongEntity> = database.songDao().getSongsFromArtist(artist)
                if (songs.isNotEmpty()) {
                    Log.d("dbMusic", "Liste des chansons présentes sur cet artiste :")
                    songs.forEach { song ->
                        Log.d("dbMusic", song.toString())
                    }
                } else {
                    Log.d("dbMusic", "Aucune chanson trouvée pour cet artiste dans la base de données.")
                }
            }  catch (e: Exception) {
                Log.e("dbMusic", "Erreur lors de la récupération des chansons : ${e.message}")
            }
        }
    }



    fun checkIfSongsExists() {
        viewModelScope.launch {
            val count = database.songDao().getSongsCount()
            _hasSongs.postValue(count > 0)
        }
    }
}