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
