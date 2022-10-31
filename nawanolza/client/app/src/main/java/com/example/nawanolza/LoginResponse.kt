package com.example.nawanolza

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("authorization")
    val name: String? = null,

)