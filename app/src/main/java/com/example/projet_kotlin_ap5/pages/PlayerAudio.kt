package com.example.projet_kotlin_ap5.pages

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projet_kotlin_ap5.R
import com.example.projet_kotlin_ap5.components.CreateFavoriteButton
import com.example.projet_kotlin_ap5.components.CreateParolesButton
import com.example.projet_kotlin_ap5.components.CreateRoundButton

@Composable
fun PlayerAudio(imageName: String?, navController: NavController) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Ajouter l'image au centre
        imageName?.let {
            Image(
                painter = painterResource(id = context.getImageResourceId(it)),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(300.dp)
                    .align(Alignment.Center)
            )
        }
        CreateFavoriteButton()
        CreateRoundButton(context, navController)
        CreateParolesButton(context)


    }
}

fun Context.getImageResourceId(imageName: String): Int {
    return resources.getIdentifier(imageName, "drawable", packageName)
}

