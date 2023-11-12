package com.silverafederico.apimarvel.data.models

data class MarvelComic(
    val issueNumber: Double,
    val title: String,
    val description: String,
    val listCharacter: MutableList<String>,
)
