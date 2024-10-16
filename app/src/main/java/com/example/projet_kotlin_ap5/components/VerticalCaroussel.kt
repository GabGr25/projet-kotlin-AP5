package com.example.projet_kotlin_ap5.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun VerticalCarousel(items: List<String>) {

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
                    ClickableImage(item, 175.dp)
                }

                if (pair.size == 1) {
                    Spacer(modifier = Modifier.width(175.dp))
                }
            }
        }
    }
}
