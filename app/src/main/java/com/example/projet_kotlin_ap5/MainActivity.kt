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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.projet_kotlin_ap5.pages.Home
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
import com.example.projet_kotlin_ap5.ui.theme.lexendFontFamily
import android.Manifest
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.lifecycle.ViewModelProvider
import com.example.projet_kotlin_ap5.entities.SongEntity
import com.example.projet_kotlin_ap5.models.SongViewModel
import com.example.projet_kotlin_ap5.models.SongViewModelFactory
import com.example.projet_kotlin_ap5.services.MusicScanner
import com.example.projet_kotlin_ap5.services.Toaster
import com.example.projet_kotlin_ap5.ui.theme.YellowColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var songViewModel: SongViewModel
    private lateinit var musicScanner: MusicScanner

    // Gérer le résultat de la demande de permission
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
    // Used to refresh all the database by scanning the phone storage
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
}
