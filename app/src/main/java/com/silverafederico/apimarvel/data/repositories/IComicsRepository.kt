package com.silverafederico.apimarvel.data.repositories

import com.silverafederico.apimarvel.data.models.MarvelComic

interface IComicsRepository {
    suspend fun fetchComics(characterId:String?):List<MarvelComic>
}