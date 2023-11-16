package com.silverafederico.apimarvel.data.repositories

import com.silverafederico.apimarvel.BuildConfig
import com.silverafederico.apimarvel.data.ApiService
import com.silverafederico.apimarvel.data.CharacterNetworkMapper
import com.silverafederico.apimarvel.data.ComicsNetworkMapper
import com.silverafederico.apimarvel.data.models.MarvelCharacter
import com.silverafederico.apimarvel.data.models.MarvelComic
import kotlinx.serialization.Serializable
import java.util.Date
@Serializable
class CharacterNameRepository(private val apiService: ApiService): ICharacterNameRepository {
    override suspend fun fetchCharacterName(comicId:String): List<MarvelCharacter>{
        val timeStamp = Date().time.toString()
        val characters = apiService.listCharactersName(
            apiKey = BuildConfig.PUBLIC_KEY,
            ts = timeStamp,
            hash = (timeStamp+ BuildConfig.PRIVATE_KEY+ BuildConfig.PUBLIC_KEY).md5().toHex(),
            comicId = comicId
        )
        return CharacterNetworkMapper.fromGetCharactersResponse(characters)
    }
}