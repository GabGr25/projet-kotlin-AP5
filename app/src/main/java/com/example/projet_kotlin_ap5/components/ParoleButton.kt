package com.example.projet_kotlin_ap5.components


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.projet_kotlin_ap5.R
import com.example.projet_kotlin_ap5.services.AudioPlayerService
import com.example.projet_kotlin_ap5.ui.theme.lexendFontFamily
import com.example.projet_kotlin_ap5.viewModel.SongViewModel

@Composable
fun CreateParolesButton(navController: NavController, audioPlayerService: AudioPlayerService, songViewModel: SongViewModel) {

    Button(
        onClick = { navController.navigate("Lyrics") },
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        modifier = Modifier
            .padding(top = 16.dp, bottom = 16.dp)
            .clip(RoundedCornerShape(50))
            .border(2.dp, Color.Gray, RoundedCornerShape(50))
            .height(50.dp)
            .width(150.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.microphone),
                contentDescription = "Microphone Icon",
                tint = Color.Gray,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Paroles",
                color = Color.Gray,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = lexendFontFamily
            )
        }
    }
}

