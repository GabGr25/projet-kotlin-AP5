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

@Composable
fun SquareView(firstColumn: List<String>, secondColumn: List<String>){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 25.dp),
        horizontalArrangement = Arrangement.Center,
    ){
        Column (
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            for ( name in firstColumn){
                ClickableImage(name, 175.dp)
            }
        }
        Spacer(modifier = Modifier.width(20.dp))
        Column (
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            for ( name in secondColumn){
                ClickableImage(name, 175.dp)
            }
        }
    }
}