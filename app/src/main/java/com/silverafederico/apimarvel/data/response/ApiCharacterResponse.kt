package com.silverafederico.apimarvel.data.response


import com.google.gson.annotations.SerializedName

data class ApiCharacterResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val data: Data,
    @SerializedName("status")
    val status: String
)