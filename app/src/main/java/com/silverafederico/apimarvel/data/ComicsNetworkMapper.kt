package com.silverafederico.apimarvel.data

import com.silverafederico.apimarvel.data.models.MarvelComic
import com.silverafederico.apimarvel.data.response.comics.ApiComicsResponse
import com.silverafederico.apimarvel.data.response.comics.Result

object ComicsNetworkMapper :EntityMapper<Result,MarvelComic>{
    override fun mapFromEntity(entity: Result): MarvelComic {
        val imagePath = entity.thumbnail.path.replace("http", "https")
        return MarvelComic(
            issueNumber = entity.issueNumber,
            title = entity.title,
            description = entity.description,
            image = "${imagePath}.${entity.thumbnail.extension}",
            listCharacter = entity.characters.items

            )
    }

    override fun mapToEntity(domainModel: MarvelComic): Result {
        TODO("Not yet implemented")
    }
    fun fromGetComicsResponse(networkResponse: ApiComicsResponse):List<MarvelComic>{
        return networkResponse.data.results.map{ ComicsNetworkMapper.mapFromEntity((it)) }
    }

}