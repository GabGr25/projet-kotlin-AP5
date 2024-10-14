package com.example.projet_kotlin_ap5.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projet_kotlin_ap5.components.TitleText
import com.example.projet_kotlin_ap5.components.VerticalCarousel

@Composable
fun Album(navController: NavController, modifier: Modifier = Modifier){
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 10.dp),
        verticalArrangement = Arrangement.Top
    ) {
        TitleText("Albums préférés", 0.55f)
        VerticalCarousel(navController,
            listOf(
                "img1",
                "img2",
                "img5",
                "img7",
                "img3",
                "img4",
                "img6",
                "img8",
                "img9",
                "img10"
            )
        )
    }
}