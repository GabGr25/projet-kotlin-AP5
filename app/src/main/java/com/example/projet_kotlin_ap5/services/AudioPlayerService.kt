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
        loadSong(currentSong!!)
        Log.d("dev", "Album loaded : $album avec ${loadedAlbum.size} chansons. Chanson en cours : ${currentSong!!.title}")
    }

    fun skipToNextSong() {
        stop()
        if (currentPlaylist != null && currentSong != null) {
            val currentIndex = currentPlaylist!!.indexOf(currentSong)

            if (currentIndex < currentPlaylist!!.size - 1) {
                currentSong = currentPlaylist!![currentIndex + 1]
            } else {
                // If we are at the end of the playlist, go to the first song
                currentSong = currentPlaylist!![0]
            }

            loadSong(currentSong!!)
            play()
        }
    }

    fun skipToPreviousSong() {
        stop()
        if (currentPlaylist != null && currentSong != null) {
            val currentIndex = currentPlaylist!!.indexOf(currentSong)
            if (currentIndex > 0) {
                currentSong = currentPlaylist!![currentIndex - 1]
            } else {
                // If we are at the beginning of the playlist, go to the last song
                currentSong = currentPlaylist!![currentPlaylist!!.size - 1]
            }

            loadSong(currentSong!!)
            play()
        }
    }


        // Media player section
        fun loadSong(songEntity: SongEntity) {
            mediaPlayer.reset()
            try {
                val filePath = File(musicDir, songEntity.pathName+"/"+songEntity.fileName).absolutePath
                mediaPlayer.setDataSource(filePath)
                mediaPlayer.prepare()
            } catch (e: Exception) {
                Log.e("dev", "Erreur lors du chargement de la chanson", e)
            }
        }

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

