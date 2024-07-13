package com.example.unittestingphilipp.data.remote

import com.example.unittestingphilipp.BuildConfig
import com.example.unittestingphilipp.data.remote.response.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayAPI {

    @GET("/api/")
    suspend fun searchForImage(
        @Query("q") searchQuery: String,
        @Query("key") apiKey: String = BuildConfig.API_KEY
    ): Response<ImageResponse>
}