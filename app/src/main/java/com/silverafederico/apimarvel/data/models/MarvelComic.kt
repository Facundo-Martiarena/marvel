package com.silverafederico.apimarvel.data.models

import com.silverafederico.apimarvel.data.response.comics.Item
import kotlinx.serialization.Serializable

@Serializable
data class MarvelComic(
    val issueNumber: Double,
    val title: String,
    val description: String,
    val image: String,
    val listCharacter: List<Item>,
)
