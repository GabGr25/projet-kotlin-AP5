package com.example.projet_kotlin_ap5.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projet_kotlin_ap5.components.CreateRoundButton
import com.example.projet_kotlin_ap5.components.TitleText
import com.example.projet_kotlin_ap5.ui.theme.lexendFontFamily
import com.example.projet_kotlin_ap5.viewModel.LyricsCached


@Composable
fun Lyrics(navController: NavController, modifier: Modifier = Modifier){

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .verticalScroll(scrollState)
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            CreateRoundButton(
                modifier = Modifier.
                    padding(top = 24.dp, start = 20.dp)
            ) { navController.navigate("player_audio/${MusicPlayed.name.value}") }
            TitleText("Lyrics :", 0.28f)
        }
        Text(
            text = LyricsCached.lyricsContent.value,
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