package com.example.projet_kotlin_ap5.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projet_kotlin_ap5.api.ApiClient
import androidx.navigation.NavController
import com.example.projet_kotlin_ap5.MusicDatabase
import com.example.projet_kotlin_ap5.services.AudioPlayerService
import kotlinx.coroutines.launch

class ParolesViewModel(private val database: MusicDatabase, private val songViewModel: SongViewModel) : ViewModel() {

    fun onParolesButtonClicked(navController: NavController, audioPlayerService: AudioPlayerService) {
        viewModelScope.launch {
            val currentSong = audioPlayerService.currentSongFlow.value

            if (currentSong != null && (currentSong.lyrics == "No Lyrics")) {
                val artist = currentSong.artist
                val title = currentSong.title

                try {
                    val response = ApiClient.apiService.getLyrics(artist, title)

                    if (response.isSuccessful) {
                        val lyrics = response.body()?.lyrics

                        if (lyrics != null) {
                            val updatedSong = currentSong.copy(lyrics = lyrics)
                            songViewModel.updateSong(updatedSong)

                            audioPlayerService.loadSong(updatedSong)
                        } else {
                            println("No lyrics found for this song")
                        }
                    } else {
                        println("Failed to fetch lyrics: ${response.code()}")
                    }
                } catch (e: Exception) {
                    println("Error fetching lyrics: ${e.message}")
                }

                navController.navigate("Lyrics")
            } else if (currentSong != null && currentSong.lyrics != "No Lyrics") {
                navController.navigate("Lyrics")
            } else {
                println("No song is currently playing")
            }
        }
    }
}