package com.example.projet_kotlin_ap5.pages

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projet_kotlin_ap5.components.ClickableImage
import com.example.projet_kotlin_ap5.components.CreateFavoriteButton
import com.example.projet_kotlin_ap5.components.CreateParolesButton
import com.example.projet_kotlin_ap5.components.CreateRoundButton
import com.example.projet_kotlin_ap5.components.Thumbnail
import com.example.projet_kotlin_ap5.ui.theme.BackgroundColor
import com.example.projet_kotlin_ap5.services.AudioPlayerService
import com.example.projet_kotlin_ap5.viewModel.SongViewModel


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun PlayerAudio(navController: NavController, songViewModel: SongViewModel, audioPlayerService: AudioPlayerService) {
    val currentSong = audioPlayerService.currentSongFlow.collectAsState(initial = null)
    val isPlaying = audioPlayerService.isPlaying.collectAsState(initial = false)
    val playlistInfos = audioPlayerService.playlistInfos.collectAsState(initial = null)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(BackgroundColor),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp)
                .padding(top = 37.dp, start = 20.dp, end = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CreateRoundButton(modifier = Modifier) { navController.navigate("album") }
            CreateFavoriteButton()
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Image et détails de la chanson courante
//        playlistInfos.value?.thumbnail?.let {
//            Image(
//                bitmap = it.asImageBitmap(),
//                contentDescription = null,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .size(280.dp)
//                    .clip(RoundedCornerShape(10.dp))
//            )
//        }
        Thumbnail(playlistInfos.value?.thumbnail, 280.dp) { }

        Text("${currentSong.value?.title}", color = Color.White, fontSize = 24.sp)
        Text("${playlistInfos.value?.name}", color = Color.LightGray, fontSize = 18.sp)
        Text("${currentSong.value?.artistId}", color = Color.DarkGray, fontSize = 18.sp)

        // Contrôles de lecture
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            ClickableImage("precedent", 55.dp, Modifier) {
                audioPlayerService.skipToPreviousSong()
            }

            ClickableImage(if (isPlaying.value) "pause" else "play", 55.dp, Modifier) {
                audioPlayerService.togglePlay()
            }

            ClickableImage("suivant", 55.dp, Modifier) {
                audioPlayerService.skipToNextSong()
            }
        }

        CreateParolesButton(navController, songViewModel = songViewModel, audioPlayerService = audioPlayerService)
    }
}
