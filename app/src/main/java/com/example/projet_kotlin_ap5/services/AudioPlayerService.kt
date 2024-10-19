package com.example.projet_kotlin_ap5.services

import android.media.MediaPlayer
import android.os.Environment
import android.util.Log
import com.example.projet_kotlin_ap5.entities.SongEntity
import com.example.projet_kotlin_ap5.models.SongViewModel
import java.io.File

class AudioPlayerService(private val songViewModel: SongViewModel) {
    val mediaPlayer = MediaPlayer()
    val musicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)

    var currentSong: SongEntity? = null
    var currentPlaylist: List<SongEntity>? = null

    suspend fun loadAlbum(album: String) {
        val loadedAlbum = songViewModel.getSongsByAlbum(album)
        currentPlaylist = loadedAlbum
        currentSong = loadedAlbum[0]
        Log.d("dev", "Album loaded : $album avec ${loadedAlbum.size} chansons. Chanson en cours : ${currentSong!!.title}")
    }

    fun skipToNextSong() {
        if (currentPlaylist != null && currentSong != null) {
            val currentIndex = currentPlaylist!!.indexOf(currentSong)
            if (currentIndex < currentPlaylist!!.size - 1) {
                currentSong = currentPlaylist!![currentIndex + 1]
            }
        }
    }

    fun skipToPreviousSong() {
        if (currentPlaylist != null && currentSong != null) {
            val currentIndex = currentPlaylist!!.indexOf(currentSong)
            if (currentIndex > 0) {
                currentSong = currentPlaylist!![currentIndex - 1]
            }
        }
    }


        // Media player section
        fun loadSong(songEntity: SongEntity): MediaPlayer {
            try {
                Log.d("dev", "Chargement de la chanson : ${songEntity.title}")
                val filePath = File(musicDir, songEntity.pathName+"/"+songEntity.fileName).absolutePath
                Log.d("dev", "Chemin du fichier : $filePath")
                mediaPlayer.setDataSource(filePath)
                mediaPlayer.prepare()
                return mediaPlayer
            } catch (e: Exception) {
                Log.e("dev", "Erreur lors du chargement de la chanson", e)
                return mediaPlayer
            }
        }

        fun togglePlay(mediaPlayer: MediaPlayer) {
            if (mediaPlayer.isPlaying) {
                pause(mediaPlayer)
            } else {
                play(mediaPlayer)
            }
        }

        fun play(mediaPlayer: MediaPlayer) {
            if (!mediaPlayer.isPlaying) {
                mediaPlayer.start()
            }
        }

        fun pause(mediaPlayer: MediaPlayer) {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
            }
        }

    }

