package com.example.projet_kotlin_ap5.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.projet_kotlin_ap5.R

@Composable
fun Thumbnail(thumbnail: Bitmap?, sizeImage: Dp, callback: ()->Unit) {
    if (thumbnail != null) {
        Image(
            bitmap = thumbnail.asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(sizeImage)
                .clip(RoundedCornerShape(10.dp))
                .clickable {
                    callback()
                },
        )
    } else {
        Image(
            painter = painterResource(id = R.drawable.default_thumbnail),
            contentDescription = "Clickable Image",
            modifier = Modifier
                .size(sizeImage)
                .clip(RoundedCornerShape(5.dp))
                .clickable {
                    callback()
                },
            contentScale = ContentScale.Crop
        )
    }
}