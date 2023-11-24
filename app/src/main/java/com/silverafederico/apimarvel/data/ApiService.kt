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
        @Query("nameStartsWith") nameStartsWith: String?,
    ): ApiCharacterResponse

    @GET("characters/{characterId}/comics")
    suspend fun listComics(
        @Path("characterId") characterId: String?,
        @Query("apikey") apiKey: String,
        @Query("ts") ts: String,
        @Query("hash") hash: String,

    ): ApiComicsResponse

    @GET("comics/{comicId}/characters")
    suspend fun listCharactersName(
        @Path("comicId") comicId: String?,
        @Query("apikey") apiKey: String,
        @Query("ts") ts: String,
        @Query("hash") hash: String,
        @Query("limit") limit: Int = 10,
    ):ApiCharacterResponse

}