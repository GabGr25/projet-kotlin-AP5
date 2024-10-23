package com.example.projet_kotlin_ap5.services

import android.media.MediaPlayer
import android.os.Environment
import android.util.Log
import com.example.projet_kotlin_ap5.entities.AlbumEntity
import com.example.projet_kotlin_ap5.entities.SongEntity
import com.example.projet_kotlin_ap5.viewModel.AlbumViewModel
import com.example.projet_kotlin_ap5.viewModel.SongViewModel
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File

class AudioPlayerService(private val songViewModel: SongViewModel) {
    val mediaPlayer = MediaPlayer()
    val musicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)

    // Current Loaded song ready to play
    private val _currentSong = MutableStateFlow<SongEntity?>(null)
    val currentSongFlow: StateFlow<SongEntity?> = _currentSong.asStateFlow()

    // Current list of songs availables for the playlist
    private val _currentPlaylist = MutableStateFlow<List<SongEntity>>(emptyList())
    val currentPlaylist: StateFlow<List<SongEntity>> = _currentPlaylist.asStateFlow()

    // Is playing boolean to know if a song is playing
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    // Current playlist infos (name, thumbnail, Artist?)
    private val _playlistInfos = MutableStateFlow<AlbumEntity?>(null)
    val playlistInfos: StateFlow<AlbumEntity?> = _playlistInfos.asStateFlow()

    // Charger un album entier
    suspend fun loadAlbum(album: AlbumEntity) {
        // Récupérer les chansons de l'album
        val loadedAlbum = songViewModel.getSongsByAlbum(album.name)
        _currentPlaylist.value = loadedAlbum
        _playlistInfos.value = album
        if (loadedAlbum.isNotEmpty()) {
            // Charger les lyrics pour chaque chanson de l'album
            val lyricsUpdateJobs = loadedAlbum.map { song ->
                songViewModel.updateLyricsForSong(song)  // Appeler la méthode pour chaque chanson
            }

            // Attendre que toutes les mises à jour de lyrics soient terminées
            lyricsUpdateJobs.awaitAll() // Ceci va attendre que tous les jobs soient complétés

            // Mettre à jour la chanson actuelle
            _currentSong.value = loadedAlbum[0]
            loadSong(_currentSong.value!!)
            Log.d("dev", "Album loaded: $album with ${loadedAlbum.size} songs. Current song: ${_currentSong.value!!.title}")
        } else {
            Log.e("AudioPlayerService", "Aucun album trouvé.")
        }
    }

    // Sauter à la chanson suivante
    fun skipToNextSong() {
        stop()
        if (_currentSong.value != null) {
            val currentIndex = _currentPlaylist.value.indexOf(_currentSong.value)

            if (currentIndex < _currentPlaylist.value.size - 1) {
                _currentSong.value = _currentPlaylist.value[currentIndex + 1]
            } else {
                _currentSong.value = _currentPlaylist.value[0] // Revenir à la première chanson si à la fin de la liste
            }

            loadSong(_currentSong.value!!)
            play()
        }
    }

    // Revenir à la chanson précédente
    fun skipToPreviousSong() {
        stop()
        if (_currentSong.value != null) {
            val currentIndex = _currentPlaylist.value.indexOf(_currentSong.value)
            if (currentIndex > 0) {
                _currentSong.value = _currentPlaylist.value[currentIndex - 1]
            } else {
                _currentSong.value = _currentPlaylist.value[_currentPlaylist.value.size - 1] // Aller à la dernière chanson si au début de la liste
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

    fun playCurrentSong() {
        if (!mediaPlayer.isPlaying) {
            play()
        }
    }

    private fun play() {
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
            _isPlaying.value = true // Mettre à jour l'état de lecture
        }
    }

    private fun pause() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            _isPlaying.value = false // Mettre à jour l'état de lecture
        }
    }

    private fun stop() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            _isPlaying.value = false // Mettre à jour l'état de lecture
        }
    }
}
