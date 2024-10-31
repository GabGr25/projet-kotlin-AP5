package com.example.projet_kotlin_ap5.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier


@Composable
fun EchapButton(callback: ()->Unit) {
    val context = LocalContext.current
    val resourceId = context.resources.getIdentifier("echap_icon", "drawable", context.packageName)

    Image(
        painter = painterResource(resourceId),
        contentDescription = "Generic echap button",
        modifier = Modifier
            .size(64.dp)
            .padding(top = 24.dp, start = 10.dp)
            .clickable {
                callback()
            }
    )
}