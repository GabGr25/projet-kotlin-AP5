package com.example.projet_kotlin_ap5.pages

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projet_kotlin_ap5.components.CreateRoundButton
import com.example.projet_kotlin_ap5.components.TitleText
import com.example.projet_kotlin_ap5.services.AudioPlayerService
import com.example.projet_kotlin_ap5.ui.theme.lexendFontFamily

@Composable
fun Lyrics(
    navController: NavController,
    audioPlayerService: AudioPlayerService,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val currentSong = audioPlayerService.currentSongFlow.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .verticalScroll(scrollState)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            CreateRoundButton(
                modifier = Modifier
                    .padding(top = 24.dp, start = 20.dp)
            ) {
                currentSong?.let { navController.navigate("player_audio/${it.id}") }
            }
            TitleText("Lyrics :", 0.36f)
        }

        if (currentSong?.lyrics != null && currentSong.lyrics != "No lyrics") {
            Log.d("updateLyrics", "song: $currentSong")
            Text(
                text = currentSong.lyrics,
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.W700,
                fontFamily = lexendFontFamily,
                modifier = modifier
                    .padding(top = 24.dp, bottom = 24.dp)
                    .padding(horizontal = 20.dp)
            )
        }
    }
}
