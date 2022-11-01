package com.example.nawanolza.retrofit

import java.io.Serializable

data class Member(
    val createdAt: String,
    val email: String,
    val id: Int,
    val image: String,
    val name: String
): Serializable {
    constructor(): this("", "", 0, "", "")
}