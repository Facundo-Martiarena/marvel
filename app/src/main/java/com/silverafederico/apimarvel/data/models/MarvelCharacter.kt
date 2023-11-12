package com.silverafederico.apimarvel.data.models

import kotlinx.serialization.Serializable

@Serializable
data class MarvelCharacter(
    val id: Int,
    val name: String,
    val description: String,
    val image: String,
) {
    val comics: MutableList<MarvelComic> = mutableListOf()
}


