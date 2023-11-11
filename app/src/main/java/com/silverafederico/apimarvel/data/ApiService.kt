package com.silverafederico.apimarvel.data

import com.silverafederico.apimarvel.data.response.ApiCharacterResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("characters")
    suspend fun listCharacters(
        @Query("apikey") apiKey: String,
        @Query("ts") ts: String,
        @Query("hash") hash: String,
    ): ApiCharacterResponse
}