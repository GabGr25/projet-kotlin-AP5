package com.example.projet_kotlin_ap5.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projet_kotlin_ap5.components.Caroussel
import com.example.projet_kotlin_ap5.components.SquareView
import com.example.projet_kotlin_ap5.components.TitleText

@Composable
fun Artiste(navController: NavController, modifier: Modifier = Modifier){
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 10.dp),
        verticalArrangement = Arrangement.Top
    ) {

        Spacer(modifier = Modifier.height(5.dp)) // Espacement entre les deux titres
    }
}