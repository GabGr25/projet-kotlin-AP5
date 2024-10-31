package com.example.projet_kotlin_ap5.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun SquareView(navcontroller: NavController, firstColumn: List<String>, secondColumn: List<String>){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 25.dp),
        horizontalArrangement = Arrangement.Center,
    ){
        Column (
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            for ( name in firstColumn){
                ClickableImage(name, 165.dp, Modifier) {
                    navcontroller.navigate("player_audio/$name")
                    //MusicPlayed.name.value = name
                }
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column (
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            for ( name in secondColumn){
                ClickableImage(name, 165.dp, Modifier) {
                    navcontroller.navigate("player_audio/$name")
                    //MusicPlayed.name.value = name
                }
            }
        }
    }
}