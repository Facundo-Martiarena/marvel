package com.silverafederico.apimarvel.data

import com.silverafederico.apimarvel.data.models.MarvelCharacter
import com.silverafederico.apimarvel.data.response.characters.ApiCharacterResponse
import com.silverafederico.apimarvel.data.response.characters.Result

object CharacterNetworkMapper: EntityMapper<Result, MarvelCharacter> {
    override fun mapFromEntity(entity : Result): MarvelCharacter {
        val imagePath = entity.thumbnail.path.replace("http", "https")
        return MarvelCharacter(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            image = "${imagePath}.${entity.thumbnail.extension}"
        )

    }
    override fun mapToEntity(domainModel: MarvelCharacter): Result {
        return null as Result
    }

    fun fromGetCharactersResponse(networkResponse: ApiCharacterResponse):List<MarvelCharacter>{
        return networkResponse.data.results.map{ mapFromEntity((it)) }
    }
}