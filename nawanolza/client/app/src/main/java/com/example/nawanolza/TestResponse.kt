package com.example.nawanolza

import com.google.gson.annotations.SerializedName

data class TestResponse(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("age")
    val age: Int? = null,
    @SerializedName("text")
    val text: String? = null,
)