package com.example.projet_kotlin_ap5

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projet_kotlin_ap5.pages.Album
import com.example.projet_kotlin_ap5.pages.Artiste
import androidx.core.content.ContextCompat
import com.example.projet_kotlin_ap5.pages.Home
import com.example.projet_kotlin_ap5.pages.Navbar
import com.example.projet_kotlin_ap5.pages.NavbarState
import com.example.projet_kotlin_ap5.pages.PlayerAudio
import com.example.projet_kotlin_ap5.ui.theme.BackgroundColor
import com.example.projet_kotlin_ap5.ui.theme.ProjetkotlinAP5Theme
import android.Manifest
import android.os.Build
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.projet_kotlin_ap5.models.SongViewModel
import com.example.projet_kotlin_ap5.models.SongViewModelFactory
import com.example.projet_kotlin_ap5.services.AudioPlayerService
import com.example.projet_kotlin_ap5.services.MusicScanner
import com.example.projet_kotlin_ap5.services.Toaster
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    private lateinit var songViewModel: SongViewModel
    private lateinit var musicScanner: MusicScanner

    // Gérer le résultat de la demande de permission
    @RequiresApi(Build.VERSION_CODES.Q)
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission accordée, récupérer les fichiers audio
            loadMusicFiles()
        } else {
            // Permission refusée
            Log.e("MainActivity", "Permission READ_MEDIA_AUDIO refusée")
        }
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = MusicDatabase.getDatabase(this)
        // Initialisation ViewModel
        songViewModel = ViewModelProvider(this, SongViewModelFactory(database))
            .get(SongViewModel::class.java)
        musicScanner = MusicScanner(this)

        // Vérifier et demander les permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO)
            != PackageManager.PERMISSION_GRANTED) {
            // Demander la permission si elle n'est pas encore accordée
            requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_AUDIO)
        } else {
            // Si la permission est déjà accordée, récupérer les fichiers audio
            loadMusicFiles()
        }

        // Observation LiveData
        songViewModel.hasSongs.observe(this) { hasSongs ->
            if (hasSongs) {
                Toaster.toastSomething(this, "Il y a des chansons dans la base de données.")
            } else {
                Toaster.toastSomething(this, "Aucune chanson trouvée dans la base de données.")
            }
        }

        // Initialisation du service de lecture audio
        val audioPlayerService = AudioPlayerService(songViewModel)
        // TODO: Remove this devLoad call just for testing purposes
        devLoad(audioPlayerService)

        setContent {
            ProjetkotlinAP5Theme {
                val navController = rememberNavController()

                // Scaffold with Navbar at the bottom
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = BackgroundColor,
                    bottomBar = {
                        Navbar(
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
                            startDestination = "Album",  // Starting screen
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

                            // For the DEV screen only
                            composable("DEV") {
                                //Play(navController = navController, songViewModel)
                            }

                            composable("player_audio/{imageName}") { backStackEntry ->
                                val imageName = backStackEntry.arguments?.getString("imageName")
                                PlayerAudio(imageName = imageName, navController = navController, audioPlayerService)
                            }
                            }
                        }
                    }
                }
            }
        }

        // Used to refresh all the database by scanning the phone storage
        @RequiresApi(Build.VERSION_CODES.Q)
        private fun loadMusicFiles() {
            Toaster.toastSomething(this, "Scan des fichiers en cours...")
            CoroutineScope(Dispatchers.IO).launch {
                val musicList = musicScanner.loadMusicFiles()

                val database = MusicDatabase.getDatabase(this@MainActivity)
                Log.d("dev", "Suppression de la DB")
                database.songDao().deleteAll()
                Log.d("dev", "Seed de la DB")
                database.songDao().insertAll(musicList)
            }
        }

        /**
         * Méthode de test pour charger une chanson de l'album "Nevermind"
         */
        private fun devLoad(audioPlayerService: AudioPlayerService) {
            // Charger les chansons de l'album de manière asynchrone
            runBlocking {
                withContext(Dispatchers.IO) {
                    audioPlayerService.loadAlbum("Nevermind")
                    audioPlayerService.currentSong?.let {
                        Log.d("dev", "Chargement de la chanson : ${it.title}")  // Vérifier le titre de la chanson
                        audioPlayerService.loadSong(
                            it
                        )
                    }
                }
            }
        }
    }
