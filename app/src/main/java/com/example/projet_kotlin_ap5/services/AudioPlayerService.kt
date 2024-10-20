package com.example.projet_kotlin_ap5.services

import android.media.MediaPlayer
import android.os.Environment
import android.util.Log
import com.example.projet_kotlin_ap5.entities.SongEntity
import com.example.projet_kotlin_ap5.models.SongViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File

class AudioPlayerService(private val songViewModel: SongViewModel) {
    val mediaPlayer = MediaPlayer()
    val musicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)

    private val _currentSong = MutableStateFlow<SongEntity?>(null)
    val currentSongFlow: StateFlow<SongEntity?> = _currentSong.asStateFlow() // Exposer StateFlow en lecture seule
    var currentPlaylist: List<SongEntity>? = null

    // Charger un album entier
    suspend fun loadAlbum(album: String) {
        val loadedAlbum = songViewModel.getSongsByAlbum(album)
        currentPlaylist = loadedAlbum
        _currentSong.value = loadedAlbum[0]
        loadSong(_currentSong.value!!)
        Log.d("dev", "Album loaded: $album with ${loadedAlbum.size} songs. Current song: ${_currentSong.value!!.title}")
    }

    // Sauter à la chanson suivante
    fun skipToNextSong() {
        stop()
        if (currentPlaylist != null && _currentSong.value != null) {
            val currentIndex = currentPlaylist!!.indexOf(_currentSong.value)

            if (currentIndex < currentPlaylist!!.size - 1) {
                _currentSong.value = currentPlaylist!![currentIndex + 1]
            } else {
                _currentSong.value = currentPlaylist!![0] // Revenir à la première chanson si à la fin de la liste
            }

            loadSong(_currentSong.value!!)
            play()
        }
    }

    // Revenir à la chanson précédente
    fun skipToPreviousSong() {
        stop()
        if (currentPlaylist != null && _currentSong.value != null) {
            val currentIndex = currentPlaylist!!.indexOf(_currentSong.value)
            if (currentIndex > 0) {
                _currentSong.value = currentPlaylist!![currentIndex - 1]
            } else {
                _currentSong.value = currentPlaylist!![currentPlaylist!!.size - 1] // Aller à la dernière chanson si au début de la liste
            }

            loadSong(_currentSong.value!!)
            play()
        }
    }

    // Charger une chanson spécifique
    fun loadSong(songEntity: SongEntity) {
        stop()
        try {
            mediaPlayer.reset()
            val filePath = File(musicDir, songEntity.pathName + "/" + songEntity.fileName).absolutePath
            mediaPlayer.setDataSource(filePath)
            mediaPlayer.prepare()
            _currentSong.value = songEntity // Mettre à jour la chanson courante
        } catch (e: Exception) {
            Log.e("dev", "Error loading song", e)
        }
    }

    // Lecture/Pause
    fun togglePlay() {
        if (mediaPlayer.isPlaying) {
            pause()
        } else {
            play()
        }
    }

    private fun play() {
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
        }
    }

    private fun pause() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        }
    }

    private fun stop() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
    }
}
