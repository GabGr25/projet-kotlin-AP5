package com.example.projet_kotlin_ap5.services

import android.content.Context
import android.widget.Toast

class Toaster {
    companion object {
        fun toastSomething(context: Context, text: String) {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }
    }
}