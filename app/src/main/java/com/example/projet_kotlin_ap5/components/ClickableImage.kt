package com.example.projet_kotlin_ap5.components

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun getImageResourceId(context: Context, nameImage: String): Int? {
    val resourceId = context.resources.getIdentifier(nameImage, "drawable", context.packageName)
    return if (resourceId != 0) resourceId else null
}

@Composable
fun ClickableImage(nameImage: String, sizeImage: Dp) {
    val context = LocalContext.current
    // Obtenir l'identifiant de l'image dynamiquement à partir du nom
    val imageId = getImageResourceId(context, nameImage)

    // Si l'image existe, l'afficher
    imageId?.let {
        Image(
            painter = painterResource(id = it),
            contentDescription = "Clickable Image",
            modifier = Modifier
                .size(sizeImage)
                .clip(RoundedCornerShape(5.dp))
                .clickable {
                    // Action lors du clic
                },
            contentScale = ContentScale.Crop // Ajustement de l'image
        )
    }
}