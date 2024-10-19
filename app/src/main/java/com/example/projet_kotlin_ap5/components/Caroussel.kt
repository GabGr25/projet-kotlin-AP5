package com.example.projet_kotlin_ap5.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projet_kotlin_ap5.pages.MusicPlayed

@Composable
fun Caroussel(navController: NavController, list: List<String>){
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 25.dp, bottom = 10.dp), // Padding général sauf sur les côtés
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp), // Padding sur les côtés (début et fin)
        horizontalArrangement = Arrangement.spacedBy(10.dp) // Espacement entre les éléments uniquement
    ) {
        for ( name in list){
            item {
                ClickableImage(name, 140.dp) {
                    navController.navigate("player_audio/$name")
                    MusicPlayed.name.value = name
                }
            }
        }
    }
}