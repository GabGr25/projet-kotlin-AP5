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
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import java.io.File

class MainActivity : ComponentActivity() {

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
            Log.e("MainActivity", "Permission READ_EXTERNAL_STORAGE refusée")
        }
    }

    // Fonction pour charger les fichiers audio à partir de MediaStore
    private fun loadMusicFiles() {
        val musicList = mutableListOf<String>()

        // Définit les colonnes que nous voulons récupérer
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DURATION
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
            val nameColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
            val durationColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)

            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
                val name = it.getString(nameColumn)
                val duration = it.getInt(durationColumn)

                // Ajouter les informations sur la musique à la liste
                musicList.add("ID: $id, Name: $name, Duration: $duration ms")
            }
        }

        // Afficher la liste des fichiers audio dans la console
        musicList.forEach { Log.d("Music", it) }
    }

//    val musicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
//    private lateinit var permissionsLauncher: ActivityResultLauncher<Array<String>>
//    private val PERMISSION_TAG = "MusicPermissions"
//
//    private fun checkPermissions(): Boolean {
//        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13+
//            ContextCompat.checkSelfPermission(
//                this, Manifest.permission.READ_MEDIA_AUDIO
//            ) == PackageManager.PERMISSION_GRANTED
//        } else { // Android 12 et inférieurs
//            ContextCompat.checkSelfPermission(
//                this, Manifest.permission.READ_EXTERNAL_STORAGE
//            ) == PackageManager.PERMISSION_GRANTED
//        }
//    }
//
//    private fun demanderPermissions() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13+
//            permissionsLauncher.launch(
//                arrayOf(Manifest.permission.READ_MEDIA_AUDIO)
//            )
//        } else { // Android 12 et inférieurs
//            permissionsLauncher.launch(
//                arrayOf(
//                    Manifest.permission.READ_EXTERNAL_STORAGE,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE
//                )
//            )
//        }
//    }
//
//    private fun initPermissionsLauncher() {
//        permissionsLauncher = registerForActivityResult(
//            ActivityResultContracts.RequestMultiplePermissions()
//        ) { permissions ->
//            var allGranted = true
//            permissions.entries.forEach { permission ->
//                if (!permission.value) {
//                    allGranted = false
//                    Log.d(PERMISSION_TAG, "Permission refusée : ${permission.key}")
//                } else {
//                    Log.d(PERMISSION_TAG, "Permission accordée : ${permission.key}")
//                }
//            }
//            if (allGranted) {
//                accéderAuxFichiers(musicDirectory)
//            } else {
//                // Informer l'utilisateur que les permissions sont nécessaires
//                Log.d(PERMISSION_TAG, "Toutes les permissions n'ont pas été accordées.")
//            }
//        }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//
//        // Initialiser le launcher des permissions
//        initPermissionsLauncher()
//
//        if (checkPermissions()) {
//            Log.d("dev", "Accès aux fichiers")
//            accéderAuxFichiers(musicDirectory)
//        } else {
//            Log.d("dev", "demande de permission")
//            demanderPermissions()
//        }
//
//        setContent {
//            ProjetkotlinAP5Theme {
//                Scaffold(
//                    modifier = Modifier.fillMaxSize(),
//                    containerColor = BackgroundColor,
//                    bottomBar = {
//                        Navbar(
//                            pathAccueil = "Android",
//                            pathMySong = "Song",
//                            paddingBottom = PaddingValues(),
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .wrapContentHeight()
//                                .height(74.dp)
//                    )}
//                ) { innerPadding ->
//
//                    Box(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(innerPadding)
//                    ) {
//                        Home(
//                            modifier = Modifier
//                        )
//                    }
//                }
//            }
//        }
//    }
//
//
//    private fun accéderAuxFichiers(directory: File) {
//
//        Log.d("dev", "Accès aux fichiers en cours...")
//
//        val musicDirectory: File? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) { // Android 10+
//            // Utiliser Scoped Storage
//            getExternalFilesDir(Environment.DIRECTORY_MUSIC)
//        } else {
//            // Utiliser l'ancien chemin
//            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
//        }
//
//
//        if (musicDirectory != null && musicDirectory.exists() && musicDirectory.isDirectory) {
//            Log.d("dev", "Listing des fichiers de ${musicDirectory.absolutePath} ...")
//
//            val filesAndDirs = musicDirectory.listFiles()
//
//            if (filesAndDirs != null) {
//                Log.d("dev", "Nombre de fichiers/dossiers dans le directory ${filesAndDirs.size}")
//
//                for (file in filesAndDirs) {
//                    Log.d("dev", "UN FICHIER")
//
//                    if (file.isFile) {
//                        Log.d("dev", "Fichier : ${file.name}")
//                    } else if (file.isDirectory) {
//                        Log.d("dev", "Dossier : ${file.name}")
//                    }
//                }
//            } else {
//                Log.d("dev", "Aucun fichier ou dossier trouvé dans le répertoire Music.")
//            }
//        } else {
//            Log.d("dev", "Le répertoire Music n'existe pas ou n'est pas un dossier.")
//        }
//    }

}
