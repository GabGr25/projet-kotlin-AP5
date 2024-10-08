package com.example.projet_kotlin_ap5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.projet_kotlin_ap5.pages.Album
import com.example.projet_kotlin_ap5.pages.Artiste
import com.example.projet_kotlin_ap5.pages.Home
import com.example.projet_kotlin_ap5.pages.Navbar
import com.example.projet_kotlin_ap5.pages.NavbarState
import com.example.projet_kotlin_ap5.ui.theme.BackgroundColor
import com.example.projet_kotlin_ap5.ui.theme.ProjetkotlinAP5Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjetkotlinAP5Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = BackgroundColor,
                    bottomBar = {
                        Navbar(
                            pathAccueil = "Android",
                            pathMySong = "Song",
                            paddingBottom = PaddingValues(),
                            selected = NavbarState.stateNavbar.value,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .height(74.dp)
                    )}
                ) { innerPadding ->

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        when(NavbarState.stateNavbar.value){
                            1->
                                Home(
                                    modifier = Modifier
                                )
                            2->
                                Album(
                                    modifier = Modifier
                                )
                            3->
                                Artiste(
                                    modifier = Modifier
                                )
                        }
                    }
                }
            }
        }
    }
}
