package com.example.projet_kotlin_ap5.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.projet_kotlin_ap5.ui.theme.YellowColor
import com.example.projet_kotlin_ap5.ui.theme.lexendFontFamily

@Composable
fun ButtonText(text: String, color: Color){
    Text(
        text = text,
        fontSize = 18.sp,
        fontFamily = lexendFontFamily,
        color = color,
        fontWeight = FontWeight.SemiBold,
    )
}