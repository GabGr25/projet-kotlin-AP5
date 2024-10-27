package com.example.projet_kotlin_ap5

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.projet_kotlin_ap5.dao.AlbumDao
import com.example.projet_kotlin_ap5.dao.ArtistDao
import com.example.projet_kotlin_ap5.dao.SongDao
import com.example.projet_kotlin_ap5.entities.AlbumEntity
import com.example.projet_kotlin_ap5.entities.ArtistEntity
import com.example.projet_kotlin_ap5.entities.SongEntity
import com.example.projet_kotlin_ap5.services.BitmapToBiteArrayConverter

@Database(entities = [SongEntity::class, AlbumEntity::class, ArtistEntity::class], version = 8)
@TypeConverters(BitmapToBiteArrayConverter::class)
abstract class MusicDatabase : RoomDatabase() {

    abstract fun songDao(): SongDao
    abstract fun albumDao(): AlbumDao
    abstract fun artistDao(): ArtistDao

    companion object {
        @Volatile
        private var INSTANCE: MusicDatabase? = null

        fun getDatabase(context: Context): MusicDatabase {
            return INSTANCE ?:Room.databaseBuilder(
                    context.applicationContext,
                    MusicDatabase::class.java,
                    "music_database"
                )
                .fallbackToDestructiveMigration()
                .build().also {
                    INSTANCE = it
                }
        }
    }
}
