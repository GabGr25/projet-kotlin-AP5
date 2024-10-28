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
fun HeartButton(isFavorite: Boolean, callback: () -> Unit) {
    var test: MutableState<Boolean> = remember { mutableStateOf(isFavorite) }

        Icon(
            painter = painterResource(id = if (test.value) R.drawable.full_heart_icon else R.drawable.empty_heart_icon),
            contentDescription = null,
            tint = Color.Red,
            modifier = Modifier
                .size(64.dp)
                .padding(top = 24.dp, end = 10.dp)
                .clickable {
                    test.value = !test.value
                    callback()
                }
        )

    }
