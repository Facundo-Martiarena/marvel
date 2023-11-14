package com.silverafederico.apimarvel.data.models

import com.silverafederico.apimarvel.data.response.comics.Item

data class MarvelComic(
    val issueNumber: Double,
    val title: String,
    val description: String,
    val image: String,
    val listCharacter: List<Item>,
)
