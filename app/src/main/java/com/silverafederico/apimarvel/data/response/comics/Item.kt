package com.silverafederico.apimarvel.data.response.comics


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Item(
    @SerializedName("name")
    val name: String,
    @SerializedName("resourceURI")
    val resourceURI: String
)