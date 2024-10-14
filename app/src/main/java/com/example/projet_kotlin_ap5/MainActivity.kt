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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projet_kotlin_ap5.pages.Album
import com.example.projet_kotlin_ap5.pages.Artiste
import com.example.projet_kotlin_ap5.pages.Navbar
import com.example.projet_kotlin_ap5.pages.NavbarState
import com.example.projet_kotlin_ap5.pages.Home
import com.example.projet_kotlin_ap5.pages.PlayerAudio
import com.example.projet_kotlin_ap5.ui.theme.BackgroundColor
import com.example.projet_kotlin_ap5.ui.theme.ProjetkotlinAP5Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjetkotlinAP5Theme {
                val navController = rememberNavController()

                // Scaffold with Navbar at the bottom
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = BackgroundColor,
                    bottomBar = {
                        Navbar(
                            pathAccueil = "Home",  // Route vers l'écran A (Home)
                            pathMySong = "Album",   // Route vers l'écran B (Album)
                            pathArtiste = "Artiste",
                            paddingBottom = PaddingValues(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .height(74.dp),
                            selected = NavbarState.stateNavbar.value,
                            navController = navController
                        )
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        // NavHost for managing navigation between different screens
                        NavHost(
                            navController = navController,
                            startDestination = "Home",  // Starting screen
                            modifier = Modifier.fillMaxSize()
                        ) {
                            // Define your composable screens here
                            composable("Home") {
                                Home(navController = navController)
                            }

                            composable("Album") {
                                Album(navController = navController)
                            }

                            composable("Artiste") {
                                Artiste(navController = navController)
                            }

                            composable("player_audio/{imageName}") { backStackEntry ->
                                val imageName = backStackEntry.arguments?.getString("imageName")
                                PlayerAudio(imageName = imageName, navController = navController)
                            }
                            }
                        }
                    }
                }
            }
        }
    }
