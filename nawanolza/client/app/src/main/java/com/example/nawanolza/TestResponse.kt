package com.example.nawanolza

import com.google.gson.annotations.SerializedName

data class TestResponse(
    @SerializedName("age")
    val age: Int? = null,
    @SerializedName("name")
    val name: String? = null
)