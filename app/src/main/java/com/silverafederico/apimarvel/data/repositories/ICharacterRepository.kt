package com.silverafederico.apimarvel.data.repositories

import com.silverafederico.apimarvel.data.models.MarvelCharacter

interface ICharacterRepository {
    suspend fun fetchCharacters(nameStartsWith:String?): List<MarvelCharacter>
}