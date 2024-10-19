package com.example.projet_kotlin_ap5.services

import com.example.projet_kotlin_ap5.entities.SongEntity
import com.example.projet_kotlin_ap5.models.SongViewModel

class PlaylistService(private val songViewModel: SongViewModel) {
    companion object {
        // Utilisée pour contenir la chanson en cours de lecture et son album
        private var currentSong: SongEntity? = null
        private var currentAlbum: List<SongEntity>? = null
    }

    suspend fun loadAlbum(album: String) {
        val loadedAlbum = songViewModel.getSongsByAlbum(album)
        currentAlbum = loadedAlbum
    }

    fun setCurrentSong(song: SongEntity) {
        currentSong = song
    }

    fun getCurrentSong(): SongEntity? {
        return currentSong
    }

    fun skipToNextSong() {
        if (currentAlbum != null && currentSong != null) {
            val currentIndex = currentAlbum!!.indexOf(currentSong)
            if (currentIndex < currentAlbum!!.size - 1) {
                currentSong = currentAlbum!![currentIndex + 1]
            }
        }
    }

    fun skipToPreviousSong() {
        if (currentAlbum != null && currentSong != null) {
            val currentIndex = currentAlbum!!.indexOf(currentSong)
            if (currentIndex > 0) {
                currentSong = currentAlbum!![currentIndex - 1]
            }
        }
    }
}