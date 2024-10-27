package com.example.projet_kotlin_ap5.viewModel

import com.example.projet_kotlin_ap5.MusicDatabase
import com.example.projet_kotlin_ap5.entities.AlbumEntity

class AlbumViewModel(private val database: MusicDatabase) {

    suspend fun getAllAlbums() = database.albumDao().getAllAlbums()

    suspend fun getFirstFiveAlbums() = database.albumDao().getFirstFiveAlbums()

    suspend fun getAlbumsFromArtist(artist: Long) = database.albumDao().getAlbumsFromArtist(artist)

    suspend fun getAlbumById(id: Long) = database.albumDao().getAlbumById(id)

    suspend fun getAlbumByName(name: String) = database.albumDao().getAlbumByName(name)

    suspend fun getAlbumThumbnailById(id: Long) = database.albumDao().getAlbumThumbnailById(id)

    suspend fun insertOne(albumEntity: AlbumEntity) = database.albumDao().insertOne(albumEntity)

    suspend fun insertAll(albums: List<AlbumEntity>) = database.albumDao().insertAll(albums)

    suspend fun updateOne(albumEntity: AlbumEntity) = database.albumDao().updateOne(albumEntity)

    suspend fun deleteAll() = database.albumDao().deleteAll()

}