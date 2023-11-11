package com.silverafederico.apimarvel.data

import com.silverafederico.apimarvel.data.response.characters.ApiCharacterResponse
import com.silverafederico.apimarvel.data.response.comics.ApiComicsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("characters")
    suspend fun listCharacters(
        @Query("apikey") apiKey: String,
        @Query("ts") ts: String,
        @Query("hash") hash: String,
    ): ApiCharacterResponse

    @GET("characters/{characterId}/comics")
    suspend fun listComics(
        @Query("apikey") apiKey: String,
        @Query("ts") ts: String,
        @Query("hash") hash: String,
        @Path("characterId") characterId: String,
    ): ApiComicsResponse
}