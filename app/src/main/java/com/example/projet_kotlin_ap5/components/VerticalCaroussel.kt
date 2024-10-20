package com.example.projet_kotlin_ap5.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun VerticalCarousel(navcontroller: NavController, items: List<String>, clickableImageCallback : (String) -> Unit) {

    val pairedItems = items.chunked(2)

    LazyColumn(
        modifier = Modifier.fillMaxWidth().padding(top = 25.dp, bottom = 25.dp),

        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(pairedItems) { pair ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (item in pair) {
                    ClickableImage(item, 175.dp) { handleAlbumClick(navcontroller, item, clickableImageCallback) }
                }

                if (pair.size == 1) {
                    Spacer(modifier = Modifier.width(175.dp))
                }
            }
        }
    }
}

fun handleAlbumClick(navController: NavController, album: String, clickableImageCallback: (String) -> Unit) {
    clickableImageCallback(album)
    navController.navigate("player_audio/$album")
}
