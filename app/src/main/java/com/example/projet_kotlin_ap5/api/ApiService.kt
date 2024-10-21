package com.example.projet_kotlin_ap5.api

import com.example.projet_kotlin_ap5.models.ApiResponseLyrics
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("/v1/{artiste}/{title}")
    suspend fun getLyrics(
        @Path("artiste") artiste: String,
        @Path("title") title: String
    ): Response<ApiResponseLyrics>
}