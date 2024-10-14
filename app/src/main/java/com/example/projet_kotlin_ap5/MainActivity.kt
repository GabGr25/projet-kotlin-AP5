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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.projet_kotlin_ap5.components.ButtonText
import com.example.projet_kotlin_ap5.components.Caroussel
import com.example.projet_kotlin_ap5.components.SquareView
import com.example.projet_kotlin_ap5.components.TitleText
import com.example.projet_kotlin_ap5.pages.Home
import com.example.projet_kotlin_ap5.pages.MediaQuery
import com.example.projet_kotlin_ap5.pages.Navbar
import com.example.projet_kotlin_ap5.ui.theme.BackgroundColor
import com.example.projet_kotlin_ap5.ui.theme.ProjetkotlinAP5Theme
import com.example.projet_kotlin_ap5.ui.theme.lexendFontFamily
import android.Manifest
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.projet_kotlin_ap5.entities.SongEntity
import com.example.projet_kotlin_ap5.models.SongViewModel
import com.example.projet_kotlin_ap5.models.SongViewModelFactory
import java.io.File
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var songViewModel: SongViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .height(74.dp)
                        )
                    }
                ) { innerPadding ->

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        Home(
                            modifier = Modifier
                        )
                    }
                }
            }
        }

        val database = MusicDatabase.getDatabase(this)

        // Initialisation ViewModel
        songViewModel = ViewModelProvider(this, SongViewModelFactory(database))
            .get(SongViewModel::class.java)
        // Observation LiveData
        songViewModel.hasSongs.observe(this) { hasSongs ->
            if (hasSongs) {
                Toast.makeText(this, "Il y a des chansons dans la base de données.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Aucune chanson trouvée dans la base de données.", Toast.LENGTH_SHORT).show()
            }
        }
        // Lancement vérification
        //songViewModel.checkIfSongsExists() OK
        //songViewModel.logAllSongs() OK
        //songViewModel.logSongsByAlbum("Autobahn") OK
        //songViewModel.logSongsByArtist("SCH") OK


        // Vérifier et demander les permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO)
            != PackageManager.PERMISSION_GRANTED) {
            // Demander la permission si elle n'est pas encore accordée
            requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_AUDIO)
        } else {
            // Si la permission est déjà accordée, récupérer les fichiers audio
            loadMusicFiles()
        }
    }

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

    // Fonction pour charger les fichiers audio à partir de MediaStore
    private fun loadMusicFiles() {
        val musicList = mutableListOf<SongEntity>()

        // Définit les colonnes que nous voulons récupérer
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.RELATIVE_PATH,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.TITLE,

//            MediaStore.Audio.Media.GENRE,
//            MediaStore.Audio.Media.CD_TRACK_NUMBER
        )

        // Filtre pour récupérer uniquement les fichiers du dossier Music
        val selection = "${MediaStore.Audio.Media.RELATIVE_PATH} LIKE ?"
        val selectionArgs = arrayOf("Music/%")

        // Query sur MediaStore pour obtenir les fichiers audio
        val cursor = contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            null
        )

        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val fileNameColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
            val durationColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
            val pathNameColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.RELATIVE_PATH)
            val albumColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)
            val titleColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val artistColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            //val genreColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.GENRE)
            //val cdTrackNumberColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.CD_TRACK_NUMBER)

            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
                val fileName = it.getString(fileNameColumn)
                val duration = it.getInt(durationColumn)
                val pathName = it.getString(pathNameColumn)
                val album = it.getString(albumColumn)
                val title = it.getString(titleColumn)
                val artist = it.getString(artistColumn)
                //val genre = it.getString(genreColumn)
                //val cdTrackNumber = it.getString(cdTrackNumberColumn)

                // Ajouter les informations sur la musique à la liste
                //musicList.add("ID: $id, Name: $name, Duration: $duration ms, pathName: $pathName")
                musicList.add(SongEntity(id, title, album, artist, duration, fileName, pathName))
            }
        }

        // Insertion des données dans la base de données
        val database = MusicDatabase.getDatabase(this)
        CoroutineScope(Dispatchers.IO).launch {
            // TODO: Faire un autre système pour éviter de vider entièrement la base de données
            database.songDao().deleteAll() // Vide la DB
            database.songDao().insertAll(musicList) // Réinsère l'ensemble des musiques
        }
    }
}
