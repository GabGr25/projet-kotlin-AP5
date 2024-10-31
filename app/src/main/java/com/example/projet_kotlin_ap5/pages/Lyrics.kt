package com.example.projet_kotlin_ap5.pages

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.projet_kotlin_ap5.components.ClickableImage
import com.example.projet_kotlin_ap5.components.EchapButton
import com.example.projet_kotlin_ap5.components.HeartButton
import com.example.projet_kotlin_ap5.services.AudioPlayerService
import com.example.projet_kotlin_ap5.ui.theme.lexendFontFamily
import com.example.projet_kotlin_ap5.R
import com.example.projet_kotlin_ap5.ui.theme.blurBackground

@SuppressLint("CoroutineCreationDuringComposition", "StateFlowValueCalledInComposition")
@Composable
fun Lyrics(
    navController: NavController,
    audioPlayerService: AudioPlayerService,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val currentSong = audioPlayerService.currentSongFlow.collectAsState().value
    val thumbnail = audioPlayerService.playlistInfos.value?.thumbnail

    if (thumbnail != null) {
        Image(
            bitmap = thumbnail.asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxHeight()
                .zIndex(0f)
                .blur(5.dp)
        )
    } else {
        Image(
            painter = painterResource(id = R.drawable.default_thumbnail),
            contentDescription = "Clickable Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxHeight()
                .zIndex(0f)
                .blur(5.dp)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .zIndex(1f)
            .background(blurBackground)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                EchapButton {
                    currentSong?.let { navController.navigate("player_audio/${it.id}") }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(horizontalAlignment = Alignment.End) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // TODO: Remplacer isFavorite par le bon état de la chanson
                    HeartButton(isFavorite = false) {
                        audioPlayerService.toggleLike()
                    }
                    ClickableImage(
                        "hamburger_icon",
                        sizeImage = 50.dp,
                        modifier = Modifier.padding(top = 24.dp, end = 10.dp)
                    ) {
                        Log.d("dev", "Affichage menu contextuel")
                    }
                }
            }
        }

        Row(modifier = Modifier.verticalScroll(scrollState).fillMaxSize()) {
            currentSong?.lyrics?.let {
                Text(
                    text = it,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.W700,
                    fontFamily = lexendFontFamily,
                    modifier = modifier
                        .padding(top = 24.dp, bottom = 24.dp)
                        .padding(horizontal = 20.dp)

                )

                if (currentSong.lyrics == "No lyrics") {
                    ClickableImage("refresh_icon", 25.dp, modifier = Modifier.absoluteOffset(x = 50.dp, y = 25.dp)) {
                        audioPlayerService.refreshCurrentSongLyrics()
                    }
                }
            }
        }
    }
}
