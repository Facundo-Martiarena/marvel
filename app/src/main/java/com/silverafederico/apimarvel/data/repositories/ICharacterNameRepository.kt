package com.silverafederico.apimarvel.data.repositories

import com.silverafederico.apimarvel.data.models.MarvelCharacter
import com.silverafederico.apimarvel.data.models.MarvelComic

interface ICharacterNameRepository {
    suspend fun fetchCharacterName(comicId:String?):List<MarvelCharacter>
}