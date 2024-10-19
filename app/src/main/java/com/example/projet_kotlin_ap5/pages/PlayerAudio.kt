package com.example.projet_kotlin_ap5.pages

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projet_kotlin_ap5.R
import com.example.projet_kotlin_ap5.components.ClickableImage
import com.example.projet_kotlin_ap5.components.CreateFavoriteButton
import com.example.projet_kotlin_ap5.components.CreateParolesButton
import com.example.projet_kotlin_ap5.components.CreateRoundButton
import com.example.projet_kotlin_ap5.models.SongViewModel
import com.example.projet_kotlin_ap5.pages.MusicPlayer.Paused
import com.example.projet_kotlin_ap5.services.AudioPlayerService
import com.example.projet_kotlin_ap5.services.PlaylistService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun PlayerAudio(imageName: String?, navController: NavController, songViewModel: SongViewModel) {
    val context = LocalContext.current

    val playlistService = PlaylistService(songViewModel = songViewModel)
    CoroutineScope(Dispatchers.IO).launch {
        playlistService.loadAlbum("Autobahn")
    }
    val currentSong = playlistService.getCurrentSong()
    // Loading song

    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    if (currentSong != null) {
        mediaPlayer = AudioPlayerService.loadSong(currentSong)
    }


    // Ajout d'une couleur de fond temporaire pour mieux visualiser la zone occupée
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Premier bloc contenant les boutons en haut
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(top = 32.dp, start = 32.dp, end = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CreateRoundButton(context, navController)
            CreateFavoriteButton()
        }

        // Bloc avec l'image (prend une partie de l'espace disponible)
        imageName?.let {
            Image(
                painter = painterResource(id = context.getImageResourceId(it)), // Vérifier l'ID de l'image
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(300.dp)
            )
        }

        // Bloc avec le bouton Pause
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            ClickableImage("precedent", 100.dp, callback = { PreviousMusic()} )
            PausePlayButton(callback = {
                 AudioPlayerService.togglePlay(mediaPlayer!!)
            })
            ClickableImage("suivant", 100.dp, callback = { NextMusic()} )
        }
        CreateParolesButton()
    }
}

fun Context.getImageResourceId(imageName: String): Int {
    // Cette méthode retourne -1 si l'image n'est pas trouvée, vérifiez le nom d'image utilisé
    return resources.getIdentifier(imageName, "drawable", packageName)
}

object MusicPlayer{
    var Paused : MutableState<Boolean> = mutableStateOf(true)
}

@Composable
fun PausePlayButton(callback: ()->Unit) {
    val context = LocalContext.current

    val isPaused = MusicPlayer.Paused

    ClickableImage(
        nameImage = if (isPaused.value) "pause" else "play",
        sizeImage = 100.dp,
        callback = {
            isPaused.value = !isPaused.value
            callback()
        }
    )
}

fun PreviousMusic(){
    //Fonction pour passer à la musique précédente
}

fun NextMusic(){
    //Fonction pour passer à la musique suivante
}