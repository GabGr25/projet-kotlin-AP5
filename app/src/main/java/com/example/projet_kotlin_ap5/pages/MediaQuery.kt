package com.example.projet_kotlin_ap5.pages

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.projet_kotlin_ap5.AudioModel
import com.example.projet_kotlin_ap5.components.TitleText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun MediaQuery(modifier: Modifier){

    val context = LocalContext.current
    val contentResolver = context.contentResolver
    var musicFiles by remember { mutableStateOf(listOf<AudioModel>()) }

    // Charger les fichiers musicaux lorsque le composable est chargé
    LaunchedEffect(Unit) {
        loadMusicFiles(contentResolver)
    }
    val rememberScrollableState = rememberScrollableState { 0F }
    Column(

        modifier = modifier
            .fillMaxSize()
            .padding(top = 10.dp)
            .scrollable(rememberScrollableState, Orientation.Vertical),
        verticalArrangement = Arrangement.Top
    ) {
        TitleText("Recherche de contenu audio", 0.66f)

        // Affichage des fichiers musicaux
        musicFiles.forEach { audioModel ->
            Text(text = "Fichier $audioModel",
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

    @SuppressLint("Recycle", "Range")
    suspend fun loadMusicFiles(contentResolver: ContentResolver) {
        return withContext(Dispatchers.IO) {
            val songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

            // Obtenir le chemin du répertoire Music
            val musicDirectoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).absolutePath

        }
    }

