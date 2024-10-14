package com.example.projet_kotlin_ap5

import SongDao
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.projet_kotlin_ap5.entities.SongEntity

@Database(entities = [SongEntity::class], version = 1)
abstract class MusicDatabase : RoomDatabase() {

    abstract fun songDao(): SongDao

    companion object {
        @Volatile
        private var INSTANCE: MusicDatabase? = null

        fun getDatabase(context: Context): MusicDatabase {
            return INSTANCE ?:Room.databaseBuilder(
                    context.applicationContext,
                    MusicDatabase::class.java,
                    "music_database"
                ).build().also {
                    INSTANCE = it
                }




        }
    }
}
