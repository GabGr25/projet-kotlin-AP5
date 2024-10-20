package com.example.projet_kotlin_ap5.pages

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projet_kotlin_ap5.components.ClickableImage
import com.example.projet_kotlin_ap5.components.TitleText
import com.example.projet_kotlin_ap5.components.VerticalCarousel
import com.example.projet_kotlin_ap5.models.SongViewModel
import com.example.projet_kotlin_ap5.services.AudioPlayerService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@Composable
fun Album(navController: NavController, songViewModel: SongViewModel, audioPlayerService: AudioPlayerService, modifier: Modifier = Modifier){

    val albums: MutableList<String> = emptyList<String>().toMutableList()
    runBlocking {
        withContext(Dispatchers.IO) {
            val rawAlbums = songViewModel.getAlbums()
            for (album in rawAlbums) {
                albums += album.lowercase().replace(" ", "_")
            }
            Log.d("dev", albums.toString())
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 10.dp),
        verticalArrangement = Arrangement.Top
    ) {
        TitleText("Albums", 0.55f)
        if (albums.isNotEmpty()) {
            VerticalCarousel(navController, albums, clickableImageCallback = { album ->
                CoroutineScope(Dispatchers.IO).launch {
                    audioPlayerService.loadAlbum(album)
                }
            })
        }
    }
}