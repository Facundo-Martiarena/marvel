package com.silverafederico.apimarvel.data.repositories

import com.silverafederico.apimarvel.BuildConfig
import com.silverafederico.apimarvel.data.ApiService
import com.silverafederico.apimarvel.data.CharacterNetworkMapper
import com.silverafederico.apimarvel.data.models.MarvelCharacter
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.util.Date

class ComicsRepository(private val apiService: ApiService): IComicsRepository {
    override suspend fun fetchComics(characterId:String): List<MarvelComic>{
        val timeStamp = Date().time.toString()
        val comics = apiService.listComics(
            apiKey = BuildConfig.PUBLIC_KEY,
            ts = timeStamp,
            hash = (timeStamp+ BuildConfig.PRIVATE_KEY+ BuildConfig.PUBLIC_KEY).md5().toHex(),
            characterId = characterId
        )
        return CharacterNetworkMapper.fromGetCharactersResponse(characters)
    }
}