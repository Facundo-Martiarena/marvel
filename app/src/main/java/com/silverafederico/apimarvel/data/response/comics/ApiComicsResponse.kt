package com.silverafederico.apimarvel.data.response.comics


import com.google.gson.annotations.SerializedName

data class ApiComicsResponse(
    @SerializedName("characters")
    val characters: Characters,
    @SerializedName("description")
    val description: String,
    @SerializedName("images")
    val images: List<Image>,
    @SerializedName("issueNumber")
    val issueNumber: Int,
    @SerializedName("title")
    val title: String,
)