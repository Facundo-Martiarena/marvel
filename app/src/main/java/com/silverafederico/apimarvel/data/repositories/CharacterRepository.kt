package com.silverafederico.apimarvel.data.repositories

import com.silverafederico.apimarvel.BuildConfig
import com.silverafederico.apimarvel.data.models.MarvelCharacter
import com.silverafederico.apimarvel.data.CharacterNetworkMapper
import com.silverafederico.apimarvel.data.MarvelClient
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.util.Date

class CharacterRepository(): ICharacterRepository {
    override suspend fun fetchCharacters(): List<MarvelCharacter>{
        val timeStamp = Date().time.toString()
        val characters = MarvelClient.service
            .listCharacters(
                apiKey = BuildConfig.PUBLIC_KEY,
                ts = timeStamp,
                hash = (timeStamp+BuildConfig.PRIVATE_KEY+BuildConfig.PUBLIC_KEY).md5().toHex()
            )
        return CharacterNetworkMapper.fromGetCharactersResponse(characters)
    }
}
fun String.md5(): ByteArray = MessageDigest.getInstance("MD5").digest(this.toByteArray(
    StandardCharsets.UTF_8
))
fun ByteArray.toHex(): String = joinToString(separator = "") { byte -> "%02x".format(byte) }