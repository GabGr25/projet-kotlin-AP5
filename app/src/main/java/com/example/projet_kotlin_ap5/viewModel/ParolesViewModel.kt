package com.example.projet_kotlin_ap5.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projet_kotlin_ap5.api.ApiClient
import androidx.navigation.NavController
import kotlinx.coroutines.launch

object LyricsCached{
    var lyricsContent : MutableState<String> = mutableStateOf("")
}

class ParolesViewModel() : ViewModel() {

    fun onParolesButtonClicked(navController: NavController) {
        viewModelScope.launch {
            /*val currentLyrics = audioPlayerService.currentSong?.lyrics
            if(currentLyrics != null && currentLyrics == "No Lyrics"){
                val artist = audioPlayerService.currentSong?.artiste
                val titre = audioPlayerService.currentSong?.titre
                val response = ApiClient.apiService.getLyrics(artist, titre)
                if (response.isSuccessful) {
                    val lyrics = response.body()?.lyrics
                    if (lyrics != null) {
                        updateSong(
                            {
                                audioPlayerService.currentSong.id,
                                title,
                                audioPlayerService.currentSong.album,
                                artist,
                                audioPlayerService.currentSong.duration,
                                audioPlayerService.currentSong.fileName,
                                audioPlayerService.currentSong.pathName,
                                lyrics
                            }
                        )
                        audioPlayerService.currentSong.lyrics = lyrics
                    } else {
                        println("No lyrics for this song")
                    }
            }
            navController.navigate("Lyrics")
            NOUVELLE FONCTION
            -----------------------------------------------------------------------------------------------------------------
            ANCIENNE FONCTION
            */
            val response = ApiClient.apiService.getLyrics("bob marley", "buffalo soldier")

            if (response.isSuccessful) {
                val lyrics = response.body()?.lyrics
                if (lyrics != null) {
                    LyricsCached.lyricsContent.value = lyrics
                } else {
                    LyricsCached.lyricsContent.value = "No lyrics for this song"
                }
            } else {
                LyricsCached.lyricsContent.value = "Error during the Request"
            }
            navController.navigate("Lyrics")
        }
    }
}
