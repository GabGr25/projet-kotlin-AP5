package com.example.projet_kotlin_ap5.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projet_kotlin_ap5.MusicDatabase

class ParolesViewModelFactory(private val database: MusicDatabase, private val songViewModel: SongViewModel): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ParolesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ParolesViewModel(database, songViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}