package com.example.projet_kotlin_ap5.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projet_kotlin_ap5.components.Caroussel
import com.example.projet_kotlin_ap5.components.SquareView
import com.example.projet_kotlin_ap5.components.TitleText

@Composable
fun Home(navController: NavController, modifier: Modifier = Modifier){
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 10.dp),
        verticalArrangement = Arrangement.Top
    ) {
        TitleText("Écoutés récemment", 0.66f)
        Caroussel(navController,listOf("img1", "img2", "img3", "img4"))

        Spacer(modifier = Modifier.height(5.dp)) // Espacement entre les deux titres

        TitleText("Playlists du moment", 0.68f)
        SquareView(navController, listOf("playlist1", "playlist2"), listOf("playlist3", "playlist4"))
    }
}