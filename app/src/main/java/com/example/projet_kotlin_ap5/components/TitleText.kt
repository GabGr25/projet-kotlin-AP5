package com.example.projet_kotlin_ap5.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projet_kotlin_ap5.ui.theme.YellowColor
import com.example.projet_kotlin_ap5.ui.theme.lexendFontFamily

@Composable
fun TitleText(text: String, sizeLigne: Float) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = text,
            fontSize = 24.sp,
            color = Color.White,
            fontWeight = FontWeight.W700,
            fontFamily = lexendFontFamily,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(top = 24.dp)

        )
        Spacer(modifier = Modifier.height(4.dp)) // Espacement entre le texte et la ligne
        Box(
            modifier = Modifier
                .height(3.dp) // Hauteur de la ligne
                .padding(horizontal = 20.dp)
                .fillMaxWidth(sizeLigne) // Largeur de la ligne
                .background(YellowColor) // Couleur de la ligne
        )
    }
}