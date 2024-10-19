package com.example.projet_kotlin_ap5.pages

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projet_kotlin_ap5.components.ClickableImage
import com.example.projet_kotlin_ap5.components.CreateFavoriteButton
import com.example.projet_kotlin_ap5.components.CreateParolesButton
import com.example.projet_kotlin_ap5.components.CreateRoundButton
import com.example.projet_kotlin_ap5.ui.theme.BackgroundColor

@Composable
fun PlayerAudio(imageName: String?, navController: NavController) {
    val context = LocalContext.current

    // Ajout d'une couleur de fond temporaire pour mieux visualiser la zone occupée
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(BackgroundColor),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Premier bloc contenant les boutons en haut
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp)
                .padding(top =37.dp, start = 20.dp, end = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CreateRoundButton(modifier = Modifier ) {navController.navigate("home")}
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
                    .clip(RoundedCornerShape(10.dp))
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
            PausePlayButton()
            ClickableImage("suivant", 100.dp, callback = { NextMusic()} )
        }
        CreateParolesButton(navController)
    }
}

fun Context.getImageResourceId(imageName: String): Int {
    // Cette méthode retourne -1 si l'image n'est pas trouvée, vérifiez le nom d'image utilisé
    return resources.getIdentifier(imageName, "drawable", packageName)
}

object MusicPlayer{
    var Paused : MutableState<Boolean> = mutableStateOf(true)
}

object MusicPlayed {
    var name: MutableState<String> = mutableStateOf("")
}

@Composable
fun PausePlayButton() {
    val isPaused = MusicPlayer.Paused

    ClickableImage(
        nameImage = if (isPaused.value) "pause" else "play",
        sizeImage = 100.dp,
        callback = {
            isPaused.value = !isPaused.value
        }
    )
}

fun PreviousMusic(){
    //Fonction pour passer à la musique précédente
}

fun NextMusic(){
    //Fonction pour passer à la musique suivante
}