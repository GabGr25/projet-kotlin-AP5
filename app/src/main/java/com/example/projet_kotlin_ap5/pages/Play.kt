package com.example.projet_kotlin_ap5.pages

import android.media.MediaPlayer
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projet_kotlin_ap5.components.TitleText
import com.example.projet_kotlin_ap5.entities.SongEntity
import com.example.projet_kotlin_ap5.models.SongViewModel
import com.example.projet_kotlin_ap5.services.AudioPlayerService

@Composable
fun Play(navController: NavController, songViewModel: SongViewModel) {
    val context = LocalContext.current
    // Obtenir l'identifiant de l'image dynamiquement à partir du nom
    val play_icon = com.example.projet_kotlin_ap5.components.getImageResourceId(context, "play_icon")
    val pause_icon = com.example.projet_kotlin_ap5.components.getImageResourceId(context, "pause_icon")
    val next_icon = com.example.projet_kotlin_ap5.components.getImageResourceId(context, "skip_forward_icon")
    val previous_icon = com.example.projet_kotlin_ap5.components.getImageResourceId(context, "skip_previous_icon")

    // State pour stocker l'album et le MediaPlayer
    var album by remember { mutableStateOf<List<SongEntity>>(emptyList()) }
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    val audioPlayerService = AudioPlayerService(songViewModel = songViewModel)


    // Charger les chansons de l'album de manière asynchrone
    LaunchedEffect(Unit) {
        val loadedAlbum = songViewModel.getSongsByAlbum("Autobahn")
        album = loadedAlbum

        if (album.isNotEmpty()) {
            mediaPlayer = audioPlayerService.loadSong(album[0])
        }
    }

    // Si l'album est vide, on affiche un message dans les logs
    if (album.isEmpty()) {
        Log.d("dev", "Aucune chanson trouvée dans l'album")
    } else {
        Log.d("dev", "Chansons trouvées dans l'album :")
//        album.forEach { song ->
//            Log.d("dev", song.toString())
//        }

        // Affichage de l'interface utilisateur une fois l'album chargé
        Column(
            verticalArrangement = Arrangement.Top
        ) {
            TitleText("DEV", 0.66f)

            // Bouton Play
            play_icon?.let {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = "Play Button",
                    modifier = androidx.compose.ui.Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .clickable {
                            Log.d("dev", "Playing")
                            if (mediaPlayer != null) {
                                audioPlayerService.togglePlay(mediaPlayer!!)
                            }
                        },
                    contentScale = ContentScale.Crop
                )
            }

            // Bouton Pause
            pause_icon?.let {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = "Pause Button",
                    modifier = androidx.compose.ui.Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .clickable {
                            Log.d("dev", "Pausing")
                            mediaPlayer?.let { audioPlayerService.togglePlay(it) }
                        },
                    contentScale = ContentScale.Crop
                )
            }

            // Bouton Suivant
            next_icon?.let {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = "Next Button",
                    modifier = androidx.compose.ui.Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .clickable {
                            //audioPlayerService.next()
                        },
                    contentScale = ContentScale.Crop
                )
            }

            // Bouton Précédent
            previous_icon?.let {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = "Previous Button",
                    modifier = androidx.compose.ui.Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .clickable {
                            //audioPlayerService.previous()
                        },
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}
