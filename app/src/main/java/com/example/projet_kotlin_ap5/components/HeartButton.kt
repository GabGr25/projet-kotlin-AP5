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
    topOffset: Dp = 32.dp,  // Paramètre pour la position verticale (décalage du haut)
    rightOffset: Dp = 32.dp  // Paramètre pour la position horizontale (décalage du côté droit)
) {
    var isFavorite by remember { mutableStateOf(false) }
    var showText by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()  // La Box prend toute la taille disponible
            .padding(top = topOffset, end = rightOffset),  // Utilisation des offsets personnalisés
        contentAlignment = Alignment.TopEnd  // Aligner le bouton en haut à droite
    ) {
        // Bouton coeur
        Icon(
            painter = painterResource(id = if (isFavorite) R.drawable.coeur else R.drawable.coeur),
            contentDescription = null,
            tint = if (isFavorite) Color.Red else Color.White,  // Changement de couleur selon l'état
            modifier = Modifier
                .size(64.dp)  // Taille de l'icône
                .clickable {
                    isFavorite = !isFavorite
                    showText = true

                    // Cacher le texte après 500ms
                    Handler(Looper.getMainLooper()).postDelayed({
                        showText = false
                    }, 500)
                }
        )

        // Texte "Ajouté aux favoris" ou "Retiré des favoris"
        if (showText) {
            Text(
                text = if (isFavorite) "Ajouté aux favoris" else "Retiré des favoris",
                color = Color.White,
                modifier = Modifier
                    .padding(top = 24.dp, end = 70.dp)  // Padding entre l'icône et le texte
                    .align(Alignment.TopEnd)
            )
        }
    }
}
