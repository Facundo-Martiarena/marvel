package com.silverafederico.apimarvel.data.repositories

interface IComicsRepository {
    suspend fun fetchComics(characterId:String):List<MarvelComic>
}