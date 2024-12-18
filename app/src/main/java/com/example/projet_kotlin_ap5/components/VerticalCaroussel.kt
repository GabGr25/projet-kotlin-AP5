package com.example.projet_kotlin_ap5.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projet_kotlin_ap5.entities.AlbumEntity

@Composable
fun VerticalCarousel(navController: NavController, items: List<AlbumEntity>, clickableImageCallback: (AlbumEntity) -> Unit) {

    val pairedItems = items.chunked(2)

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 25.dp, bottom = 25.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(pairedItems) { pair ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (item in pair) {
                    Thumbnail(item.thumbnail, 165.dp) { handleAlbumClick(navController, item, clickableImageCallback) }
                }

                if (pair.size == 1) {
                    Spacer(modifier = Modifier.width(165.dp))
                }
            }
        }
    }
}

fun handleAlbumClick(navController: NavController, album: AlbumEntity, clickableImageCallback: (AlbumEntity) -> Unit) {
    clickableImageCallback(album)
    navController.navigate("player_audio/${album.name}")
}
