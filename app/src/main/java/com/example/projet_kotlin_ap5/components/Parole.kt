package com.example.projet_kotlin_ap5.components

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.example.projet_kotlin_ap5.R

@Composable
fun CreateParolesButton(context: Context): Button {
    val buttonBackground = GradientDrawable().apply {
        shape = GradientDrawable.RECTANGLE
        cornerRadius = 70f
        setColor(Color.TRANSPARENT)
        setStroke(4, Color.GRAY)
    }

    return Button(context).apply {
        text = "Paroles"
        textSize = 18f
        setTextColor(Color.GRAY)
        background = buttonBackground
        setPadding(20, 40, 20, 10)

        val icon = ContextCompat.getDrawable(context, R.drawable.microphone)
        icon?.let {
            val wrappedIcon = DrawableCompat.wrap(it)
            DrawableCompat.setTint(wrappedIcon, Color.GRAY)
            wrappedIcon.setBounds(0, 0, 50, 50)
            setCompoundDrawables(wrappedIcon, null, null, null)
        }

        compoundDrawablePadding = 10

        layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
            setMargins(16, 0, 16, 32)
        }

        setOnClickListener {
            Toast.makeText(context, "Bouton 'Paroles' cliqué!", Toast.LENGTH_SHORT).show()
        }
    }
}