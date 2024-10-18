package com.example.projet_kotlin_ap5.pages

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projet_kotlin_ap5.components.TitleText

@Composable
fun Play(navController: NavController) {
    val context = LocalContext.current
    // Obtenir l'identifiant de l'image dynamiquement à partir du nom
    val play_icon = com.example.projet_kotlin_ap5.components.getImageResourceId(context, "play_icon")
    val pause_icon = com.example.projet_kotlin_ap5.components.getImageResourceId(context, "pause_icon")
    val next_icon = com.example.projet_kotlin_ap5.components.getImageResourceId(context, "skip_forward_icon")
    val previous_icon = com.example.projet_kotlin_ap5.components.getImageResourceId(context, "skip_previous_icon")

    // Media player
    val mediaPlayer = loadSong(context)

    Column(
        verticalArrangement = Arrangement.Top
    ) {
        TitleText("DEV", 0.66f)
        play_icon?.let {
            Image(
                painter = painterResource(id = it),
                contentDescription = "Clickable Image",
                modifier = androidx.compose.ui.Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .clickable {
                        Log.d("dev", "Playing")
                        if (mediaPlayer != null) togglePlay(mediaPlayer)
                    },
                contentScale = ContentScale.Crop // Ajustement de l'image
            )
        }
        pause_icon?.let {
            Image(
                painter = painterResource(id = it),
                contentDescription = "Clickable Image",
                modifier = androidx.compose.ui.Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .clickable {
                        Log.d("dev", "Pausing")
                        if (mediaPlayer != null) togglePlay(mediaPlayer)
                    },
                contentScale = ContentScale.Crop // Ajustement de l'image
            )
        }
        next_icon?.let {
            Image(
                painter = painterResource(id = it),
                contentDescription = "Clickable Image",
                modifier = androidx.compose.ui.Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .clickable {
                        next()
                    },
                contentScale = ContentScale.Crop // Ajustement de l'image
            )
        }
        previous_icon?.let {
            Image(
                painter = painterResource(id = it),
                contentDescription = "Clickable Image",
                modifier = androidx.compose.ui.Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .clickable {
                        previous()
                    },
                contentScale = ContentScale.Crop // Ajustement de l'image
            )
        }
    }
}

fun loadSong(context: Context): MediaPlayer? {
    // Identifier la ressource dans le dossier "audio" avec le nom du fichier, sans extension
    val resourceId = context.resources.getIdentifier("or_noir", "raw", context.packageName)

    if (resourceId != 0) {
        val mediaPlayer = MediaPlayer.create(context, resourceId)
        return mediaPlayer
    } else {
        Log.e("dev", "La ressource audio est introuvable")
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
    Log.d("dev", "Next")
}

fun previous() {
    Log.d("dev", "Previous")
}