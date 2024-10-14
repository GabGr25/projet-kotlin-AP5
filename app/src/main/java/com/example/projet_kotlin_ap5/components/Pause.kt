package com.example.projet_kotlin_ap5.components

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.projet_kotlin_ap5.R
import com.example.projet_kotlin_ap5.components.MusicPlayer.Paused
import com.example.projet_kotlin_ap5.pages.getImageResourceId

object MusicPlayer{
    var Paused : MutableState<Boolean> = mutableStateOf(true)
}

@Composable
fun PauseMusic() {
    val context = LocalContext.current
        if (Paused.value) {
            Icon(
            painter = painterResource(context.getImageResourceId("pause")),
            contentDescription = "Pause Icon",
            tint = colorResource(id = R.color.white),
            modifier = Modifier.size(100.dp)
            )
        } else {
            Icon(
            painter = painterResource(context.getImageResourceId("play")),
            contentDescription = "Play Icon",
            tint = colorResource(id = R.color.white),
            modifier = Modifier.size(100.dp)
            )
        }
}