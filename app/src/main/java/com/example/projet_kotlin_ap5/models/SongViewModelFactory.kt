package com.example.projet_kotlin_ap5.models

import androidx.lifecycle.ViewModel

import androidx.lifecycle.ViewModelProvider
import com.example.projet_kotlin_ap5.MusicDatabase

class SongViewModelFactory(private val database: MusicDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SongViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SongViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")    }
}