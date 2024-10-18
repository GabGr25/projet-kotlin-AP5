package com.example.projet_kotlin_ap5.services

import android.media.MediaPlayer
import android.os.Environment
import android.util.Log
import com.example.projet_kotlin_ap5.entities.SongEntity
import java.io.File

class AudioPlayerService {

    companion object {
        val mediaPlayer = MediaPlayer()
        val musicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)

        fun loadSong(songEntity: SongEntity): MediaPlayer? {
            try {
                val filePath = File(musicDir, songEntity.pathName+"/"+songEntity.fileName).absolutePath
                mediaPlayer.setDataSource(filePath)
                mediaPlayer.prepare()
                return mediaPlayer
            } catch (e: Exception) {
                Log.e("dev", "Erreur lors du chargement de la chanson", e)
                return null
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

        fun next() {
            //this.next()
        }

        fun previous() {
            //this.previous()
        }
    }
}