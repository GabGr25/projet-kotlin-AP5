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

fun getImageResourceId(context: Context, nameImage: String): Int? {
    val resourceId = context.resources.getIdentifier(nameImage, "drawable", context.packageName)
    return if (resourceId != 0) resourceId else null
}

@Composable
fun Play(navController: NavController) {
    val context = LocalContext.current
    // Obtenir l'identifiant de l'image dynamiquement à partir du nom
    val imageId = com.example.projet_kotlin_ap5.components.getImageResourceId(context, "img1")

    Column(
        verticalArrangement = Arrangement.Top
    ) {
        TitleText("Écoutés récemment", 0.66f)
        imageId?.let {
            Image(
                painter = painterResource(id = it),
                contentDescription = "Clickable Image",
                modifier = androidx.compose.ui.Modifier
                    .size(140.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .clickable {
                        Log.d("dev", "Playing")
                        playKaaris(context)
                    },
                contentScale = ContentScale.Crop // Ajustement de l'image
            )
        }
    }
}

fun playKaaris(context: Context) {
    // Identifier la ressource dans le dossier "audio" avec le nom du fichier, sans extension
    val resourceId = context.resources.getIdentifier("or_noir", "raw", context.packageName)

    if (resourceId != 0) {
        val mediaPlayer = MediaPlayer.create(context, resourceId)
        mediaPlayer?.start() // Démarre la lecture
    } else {
        Log.e("dev", "La ressource audio est introuvable")
    }
}