package com.example.projet_kotlin_ap5.components

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.projet_kotlin_ap5.R

@Composable
fun CreateFavoriteButton(
) {
    var isFavorite by remember { mutableStateOf(false) }
    var showText by remember { mutableStateOf(false) }


        Icon(
            painter = painterResource(id = if (isFavorite) R.drawable.coeur else R.drawable.coeur),
            contentDescription = null,
            tint = if (isFavorite) Color.Red else Color.White,  // Changement de couleur selon l'état
            modifier = Modifier
                .size(58.dp)  // Taille de l'icône
                .clickable {
                    isFavorite = !isFavorite
                    showText = true

                    // Cacher le texte après 500ms
                    Handler(Looper.getMainLooper()).postDelayed({
                        showText = false
                    }, 500)
                }
        )

    }
