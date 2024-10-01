package com.example.projet_kotlin_ap5.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.projet_kotlin_ap5.components.ButtonText
import com.example.projet_kotlin_ap5.ui.theme.NavbarBackgroundColor
import com.example.projet_kotlin_ap5.ui.theme.YellowColor

@Composable
fun Navbar(pathAccueil: String, pathMySong: String, paddingBottom: PaddingValues, modifier: Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()  // Prend toute la largeur
            .wrapContentHeight()  // Seulement la hauteur nécessaire
            .border(1.dp, Color(16,16,16))
            .background(NavbarBackgroundColor)
            .padding(bottom = 30.dp)
            .padding(top = 10.dp)
    ) {
        Button(
            onClick = { /* TODO: Action pour le bouton Home */ },
            colors = ButtonDefaults.buttonColors(
                NavbarBackgroundColor,
                YellowColor
            )
        ) {
            ButtonText("Home")
        }
        VerticalDivider(Modifier.height(20.dp))
        Button(
            onClick = { /* TODO: Action pour le bouton Favorite */ },
            colors = ButtonDefaults.buttonColors(
                NavbarBackgroundColor,
                YellowColor
            )
        ) {
            ButtonText("Album")
        }
        VerticalDivider(Modifier.height(20.dp))
        Button(
            onClick = { /* TODO: Action pour le bouton Settings */ },
            colors = ButtonDefaults.buttonColors(
                NavbarBackgroundColor,
                YellowColor
            )
        ) {
            ButtonText("Artiste")
        }
    }
}
