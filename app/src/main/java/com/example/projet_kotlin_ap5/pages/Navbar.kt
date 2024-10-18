package com.example.projet_kotlin_ap5.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projet_kotlin_ap5.components.ButtonText
import com.example.projet_kotlin_ap5.ui.theme.NavbarBackgroundColor
import com.example.projet_kotlin_ap5.ui.theme.WhiteColor
import com.example.projet_kotlin_ap5.ui.theme.YellowColor


object NavbarState{
    var stateNavbar: MutableState<Int> = mutableStateOf(1)
}

@Composable
fun Navbar(
    modifier: Modifier,
    selected: Int,
    navController: NavController,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()  // Prend toute la largeur
            .wrapContentHeight()  // Seulement la hauteur nécessaire
            .border(1.dp, Color(16, 16, 16))
            .background(NavbarBackgroundColor)
            .padding(bottom = 25.dp)
            .padding(top = 10.dp)
    ) {
        NavbarButton("Home", isSelected = (selected == 1), modifier = Modifier.weight(1f)) {
            NavbarState.stateNavbar.value = 1
            navController.navigate("Home")  // Navigation vers ScreenA
        }
//        VerticalDivider(Modifier.height(20.dp))
//        NavbarButton("Album", isSelected = (selected == 2), modifier = Modifier.weight(1f)) {
//            NavbarState.stateNavbar.value = 2
//            navController.navigate("Album")  // Navigation vers ScreenB
//        }
//        VerticalDivider(Modifier.height(20.dp))
//        NavbarButton("Artiste", isSelected = (selected == 3), modifier = Modifier.weight(1f)) {
//            NavbarState.stateNavbar.value = 3
//            navController.navigate("Artiste")
//        }
        VerticalDivider(Modifier.height(20.dp))
        NavbarButton("DEV", isSelected = (selected == 4), modifier = Modifier.weight(1f)) {
            NavbarState.stateNavbar.value = 3
            navController.navigate("DEV")
        }
    }
}


@Composable
fun NavbarButton(text: String, isSelected: Boolean, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        if (isSelected) {
            Box(
                modifier = Modifier
                    .height(3.dp)
                    .width(30.dp)
                    .background(YellowColor)
            )
        }
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                NavbarBackgroundColor,
                if (isSelected) YellowColor else WhiteColor
            )
        ) {
            ButtonText(text, if (isSelected) YellowColor else WhiteColor)
        }
    }
}